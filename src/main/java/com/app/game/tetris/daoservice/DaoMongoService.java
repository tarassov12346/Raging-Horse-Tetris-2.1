package com.app.game.tetris.daoservice;

import com.app.game.tetris.model.Game;
import com.app.game.tetris.model.SavedGame;

public interface DaoMongoService {
    void runMongoServer();

    void prepareMongoDB();

    void prepareMongoDBForNewPLayer(String playerName);

    boolean isFilePresentInMongoDB(String fileName);

    boolean isMongoDBNotEmpty();

    void cleanMongodb(String playerName, String fileName);

    void loadSavedGameIntoMongodb(SavedGame savedGame, Game player);

    SavedGame loadSavedGameFromMongodb(Game player);

    void loadSnapShotIntoMongodb(String playerName, String fileName);

    void makeDesktopSnapshot(String fileNameDetail);

    byte[] loadByteArrayFromMongodb(String playerName, String fileName);
}
