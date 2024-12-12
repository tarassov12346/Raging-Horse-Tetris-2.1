package com.app.game.tetris.config;

import com.app.game.tetris.model.Game;
import com.app.game.tetris.model.Tetramino;
import com.app.game.tetris.service.GameLogic;
import com.app.game.tetris.serviceImpl.Stage;
import com.app.game.tetris.serviceImpl.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
public class StartGameConfiguration {

    @Autowired
    private ApplicationContext context;

    public Game createGame(String playerName) {
        return context.getBean(Game.class, playerName, 0);
    }

    public State initiateState(String playerName) {
        Stage emptyStage = context.getBean(Stage.class, makeEmptyMatrix(), getTetramino0(), 0, 0, 0);
        State initialState = context.getBean(State.class, emptyStage, false, createGame(playerName));
        return initialState.start().createStateWithNewTetramino().orElse(initialState);
    }

    private char[][] makeEmptyMatrix() {
        final char[][] c = new char[GameLogic.HEIGHT][GameLogic.WIDTH];
        IntStream.range(0, GameLogic.HEIGHT).forEach(y -> IntStream.range(0, GameLogic.WIDTH).forEach(x -> c[y][x] = '0'));
        return c;
    }

    private Tetramino getTetramino0() {
        return context.getBean(Tetramino.class, (Object) new char[][]{{'0'}});
    }
}
