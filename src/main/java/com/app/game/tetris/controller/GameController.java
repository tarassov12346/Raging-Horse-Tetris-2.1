package com.app.game.tetris.controller;

import com.app.game.tetris.config.PlayGameConfiguration;
import com.app.game.tetris.config.RestartGameConfiguration;
import com.app.game.tetris.config.SaveGameConfiguration;
import com.app.game.tetris.config.StartGameConfiguration;
import com.app.game.tetris.daoservice.DaoGameService;
import com.app.game.tetris.daoservice.DaoMongoService;
import com.app.game.tetris.daoservice.DaoUserService;
import com.app.game.tetris.model.Game;
import com.app.game.tetris.model.Roles;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.model.User;
import com.app.game.tetris.serviceImpl.State;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.OutputStream;
import java.util.*;

@Controller
public class GameController {
    private HttpSession currentSession;
    private Game player;
    private State state;

    @Autowired
    private StartGameConfiguration startGameConfiguration;

    @Autowired
    private PlayGameConfiguration playGameConfiguration;

    @Autowired
    private DaoGameService daoGameService;

    @Autowired
    private DaoMongoService daoMongoService;

    @Autowired
    private SaveGameConfiguration saveGameConfiguration;

    @Autowired
    private RestartGameConfiguration restartGameConfiguration;

    @Autowired
    private DaoUserService daoUserService;

    @GetMapping({"/register"})
    public String registration(Model model) {
        if (daoUserService.isRolesDBEmpty()) {
            daoUserService.prepareRolesDB();
            daoUserService.prepareUserDB();
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords do not match!");
            return "registration";
        }
        if (!daoUserService.saveUser(userForm)) {
            model.addAttribute("usernameError", "The player with such name already exists!");
            return "registration";
        }
        return "registered";
    }

    @GetMapping({"/registered"})
    public String registrationOK(Model model) {
        return "registered";
    }

    @PostMapping("/upload")
    public String handleFileUpload(
            @RequestParam("image") MultipartFile file) {
        daoMongoService.cleanImageMongodb(player.getPlayerName(), "");
        daoMongoService.loadMugShotIntoMongodb(player.getPlayerName(), file);
        return "profile";
    }

    @GetMapping({
            "/hello"
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String hello() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        currentSession = attr.getRequest().getSession(true);
        String playerName = daoUserService.retrievePlayerName();
        player = startGameConfiguration.createGame(playerName);
        state = startGameConfiguration.initiateState(playerName);
        daoGameService.retrieveScores();
 //       daoMongoService.runMongoServer();
        makeHelloView();
        return "hello";
    }

    @GetMapping({"/start"})
    public String gameStart() {
        initiateView();
        makeGamePageView();
        return "index";
    }

    @GetMapping({"/profile"})
    public String profile() {
        daoGameService.retrievePlayerScores(player);
        if (!daoMongoService.isImageFilePresentInMongoDB(player.getPlayerName()))
            daoMongoService.prepareMongoDBForNewPLayer(player.getPlayerName());
        makeProfileView();
        return "profile";
    }

    @GetMapping({"/admin"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String administrate() {
        makeAdminPageView();
        return "admin";
    }

    @GetMapping("/admin/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        if (daoUserService.findUserById(userId).getUsername().equals(player.getPlayerName())) return "redirect:/admin";
        for (Roles role : daoUserService.findUserByUserName(player.getPlayerName()).getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                daoMongoService.cleanSavedGameMongodb(daoUserService.findUserById(userId).getUsername());
                daoMongoService.cleanImageMongodb(daoUserService.findUserById(userId).getUsername(), "");
                daoMongoService.cleanImageMongodb(daoUserService.findUserById(userId).getUsername(), "deskTopSnapShot");
                daoMongoService.cleanImageMongodb(daoUserService.findUserById(userId).getUsername(), "deskTopSnapShotBest");
                daoGameService.deleteByName(daoUserService.findUserById(userId).getUsername());
                daoUserService.deleteUser(userId);
                return "redirect:/admin";
            }
        }
        return "redirect:/admin";
    }

    @GetMapping({"/{moveId}"})
    public String gamePlay(@PathVariable Integer moveId) {
        switch (moveId) {
            case 0 -> {
                Optional<State> moveDownState = playGameConfiguration.moveDownState(state);
                if (moveDownState.isEmpty()) {
                    Optional<State> newTetraminoState = playGameConfiguration.newTetraminoState(state);
                    if (newTetraminoState.isEmpty()) {
                        currentSession.setAttribute("isGameOn", false);
                        currentSession.setAttribute("gameStatus", "PLEASE WAIT!!!");
                        state.stop();
                    } else state = newTetraminoState.orElse(state);
                }
                state = moveDownState.orElse(state);
            }
            case 1 -> {
                state = playGameConfiguration.moveDownState(state).orElse(state);
                state = playGameConfiguration.rotateState(state);
            }
            case 2 -> {
                state = playGameConfiguration.moveDownState(state).orElse(state);
                state = playGameConfiguration.moveLeftState(state);
            }
            case 3 -> {
                state = playGameConfiguration.moveDownState(state).orElse(state);
                state = playGameConfiguration.moveRightState(state);
            }
            case 4 -> state = playGameConfiguration.dropDownState(state);
        }
        makeGamePageView();
        return "index";
    }

    @GetMapping({"/5"})
    public String finalScene() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        currentSession = attr.getRequest().getSession(true);
        currentSession.setAttribute("gameStatus", "Game over");
        daoGameService.recordScore(player);
        daoGameService.retrieveScores();
        daoMongoService.makeDesktopSnapshot("deskTopSnapShot");
        daoMongoService.cleanImageMongodb(player.getPlayerName(), "deskTopSnapShot");
        daoMongoService.loadSnapShotIntoMongodb(player.getPlayerName(), "deskTopSnapShot");
        if (player.getPlayerScore() >= daoGameService.getPlayerBestScore()) {
            daoMongoService.makeDesktopSnapshot("deskTopSnapShotBest");
            daoMongoService.cleanImageMongodb(player.getPlayerName(), "deskTopSnapShotBest");
            daoMongoService.loadSnapShotIntoMongodb(player.getPlayerName(), "deskTopSnapShotBest");
        }
        makeGamePageView();
        return "snapshot";
    }

    @GetMapping({"/6"})
    public String snapShot() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        currentSession = attr.getRequest().getSession(true);
        currentSession.setAttribute("gameStatus", "Game over");
        makeGamePageView();
        return "snapshot";
    }

    @GetMapping({"/save"})
    public String gameSave() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        currentSession = attr.getRequest().getSession(true);
        state.setPause();
        currentSession.setAttribute("isGameOn", false);
        SavedGame savedGame = saveGameConfiguration.saveGame(player, state);
        daoMongoService.loadSavedGameIntoMongodb(savedGame, player);
        currentSession.setAttribute("gameStatus", "Game Saved");
        makeGamePageView();
        return "snapshot";
    }

    @GetMapping({"/restart"})
    public String gameRestart() {
        if (daoMongoService.isSavedGamePresentInMongoDB(player.getPlayerName() + "SavedGame")) {
            state = restartGameConfiguration.recreateStateFromSavedGame(daoMongoService.loadSavedGameFromMongodb(player));
            initiateView();
            makeGamePageView();
            return "index";
        }
        return "index";
    }

    @GetMapping({"/getPhoto"})
    public void getPhoto(HttpServletRequest request,
                         HttpServletResponse response) {
        byte[] imagenEnBytes = daoMongoService.loadByteArrayFromMongodb(player.getPlayerName(), "mugShot");
        response.setHeader("Accept-ranges", "bytes");
        response.setContentType("image/jpeg");
        response.setContentLength(imagenEnBytes.length);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Transfer-Encoding:", "binary");
        try {
            OutputStream out = response.getOutputStream();
            out.write(imagenEnBytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping({"/getSnapShot"})
    public void getSnapShot(HttpServletRequest request,
                            HttpServletResponse response) {
        byte[] imagenEnBytes = daoMongoService.loadByteArrayFromMongodb(player.getPlayerName(), "deskTopSnapShot");
        response.setHeader("Accept-ranges", "bytes");
        response.setContentType("image/jpeg");
        response.setContentLength(imagenEnBytes.length);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Transfer-Encoding:", "binary");
        try {
            OutputStream out = response.getOutputStream();
            out.write(imagenEnBytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping({"/getSnapShotBest"})
    public void getSnapShotBest(HttpServletRequest request,
                                HttpServletResponse response) {
        byte[] imagenEnBytes = daoMongoService.loadByteArrayFromMongodb(player.getPlayerName(), "deskTopSnapShotBest");
        response.setHeader("Accept-ranges", "bytes");
        response.setContentType("image/jpeg");
        response.setContentLength(imagenEnBytes.length);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Content-Description", "File Transfer");
        response.setHeader("Content-Transfer-Encoding:", "binary");
        try {
            OutputStream out = response.getOutputStream();
            out.write(imagenEnBytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ExceptionHandler(value = {AccessDeniedException.class, NullPointerException.class})
    public ResponseEntity<Object> handleAnyException() {
        return new ResponseEntity<>("You have no access to this function", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


    private void initiateView() {
        currentSession.setAttribute("gameStatus", "Game is ON");
        currentSession.setAttribute("isGameOn", true);
        state.unsetPause();
    }

    private void makeHelloView() {
        player = state.getGame();
        currentSession.setAttribute("player", player.getPlayerName());
        currentSession.setAttribute("bestPlayer", daoGameService.getBestPlayer());
        currentSession.setAttribute("bestScore", daoGameService.getBestScore());
    }

    private void makeProfileView() {
        player = state.getGame();
        currentSession.setAttribute("player", player.getPlayerName());
        currentSession.setAttribute("playerBestScore", daoGameService.getPlayerBestScore());
        currentSession.setAttribute("playerAttemptsNumber", daoGameService.getPlayerAttemptsNumber());
    }

    private void makeGamePageView() {
        char[][] cells = state.getStage().drawTetraminoOnCells();
        player = state.getGame();
        state.setStepDown(player.getPlayerScore() / 10 + 1);
        currentSession.setAttribute("player", player.getPlayerName());
        currentSession.setAttribute("score", player.getPlayerScore());
        currentSession.setAttribute("bestplayer", daoGameService.getBestPlayer());
        currentSession.setAttribute("bestscore", daoGameService.getBestScore());
        currentSession.setAttribute("stepdown", state.getStepDown());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                currentSession.setAttribute(new StringBuilder("cells").append(i).append("v").append(j).toString(),
                        new StringBuilder("/img/").append(cells[i][j]).append(".png").toString());
            }
        }
    }

    private void makeAdminPageView() {
        currentSession.setAttribute("allUsers", daoUserService.getAllUsers());
        currentSession.setAttribute("playersResults", getAllBestResults(daoGameService.getAllGames()));
    }

    private Set<Game> getAllBestResults(List<Game> playersList) {
        Set<Game> highestScoringPlayers = new HashSet<>();
        playersList.sort(Comparator.comparingInt(Game::getPlayerScore).reversed());
        highestScoringPlayers.addAll(playersList);
        return highestScoringPlayers;
    }
}
