package com.app.game.tetris.config;

import com.app.game.tetris.model.Game;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.model.Tetramino;
import com.app.game.tetris.serviceImpl.Stage;
import com.app.game.tetris.serviceImpl.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestartGameConfiguration {

    @Autowired
    private ApplicationContext context;

    public State recreateStateFromSavedGame(SavedGame savedGame) {
        Game game = context.getBean(Game.class, savedGame.getPlayerName(), savedGame.getPlayerScore());
        Stage recreatedStage = context.getBean(Stage.class, savedGame.getCells(), getTetramino0(), 0, 0, game.getPlayerScore() / 10);
        return context.getBean(State.class, recreatedStage, true, game).restartWithNewTetramino().orElse(context.getBean(State.class, recreatedStage, true, game));
    }

    private Tetramino getTetramino0(){
        ApplicationContext context =new AnnotationConfigApplicationContext("com.app.game.tetris.model");
        return context.getBean(Tetramino.class, (Object) new char[][]{{'0'}});
    }
}
