package com.app.game.tetris.daoservice;

import com.app.game.tetris.model.User;

import java.util.List;

public interface PlayerService {
    String retrievePlayerName();
    List<User> getAllUsers();
    boolean deleteUser(Long userId);
}


