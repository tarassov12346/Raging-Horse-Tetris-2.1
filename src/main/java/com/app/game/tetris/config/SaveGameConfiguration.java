package com.app.game.tetris.config;

import com.app.game.tetris.model.Game;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.serviceImpl.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaveGameConfiguration {

    @Autowired
    private ApplicationContext context;

    public SavedGame saveGame(Game game, State state) {
        return context.getBean(SavedGame.class,game.getPlayerName(), game.getPlayerScore(), state.getStage().getCells());
    }
}
