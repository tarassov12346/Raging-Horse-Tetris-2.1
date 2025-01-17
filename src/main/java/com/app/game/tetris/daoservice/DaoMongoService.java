package com.app.game.tetris.daoservice;

import com.app.game.tetris.model.Game;
import com.app.game.tetris.model.SavedGame;
import org.springframework.web.multipart.MultipartFile;

public interface DaoMongoService {
    void runMongoServer();

    void prepareMongoDBForNewPLayer(String playerName);

    boolean isImageFilePresentInMongoDB(String fileName);

    boolean isSavedGamePresentInMongoDB(String fileName);

    void cleanImageMongodb(String playerName, String fileName);

    void cleanSavedGameMongodb(String playername);

    void loadSavedGameIntoMongodb(SavedGame savedGame, Game player);

    SavedGame loadSavedGameFromMongodb(Game player);

    void loadSnapShotIntoMongodb(String playerName, String fileName);

    void loadMugShotIntoMongodb(String playerName, MultipartFile file);

    void makeDesktopSnapshot(String fileNameDetail);

    byte[] loadByteArrayFromMongodb(String playerName, String fileName);
}
