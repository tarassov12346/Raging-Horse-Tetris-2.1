package com.app.game.tetris.daoservice;

import com.app.game.tetris.model.Player;

import java.util.List;

public interface DaoService {
    void recordScore(Player player);

    void retrieveScores();

    void retrievePlayerScores(Player player);

    List<Player> getAllPlayers();

    String getBestPlayer();

    int getBestScore();

    int getPlayerBestScore();

    int getPlayerAttemptsNumber();
}
