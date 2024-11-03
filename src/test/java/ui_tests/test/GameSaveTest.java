package ui_tests.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GameSaveTest extends CommonConditions {

    @Test(description = "does the restarted game have the same configuration of fallen tetraminos as the saved one")
    public void doesSavedGameRestart() {
        log.info("doesSavedGameRestart Test start");
        Assert.assertEquals(savedGameCells, restartedGameCells);
    }

    @Test(description = "does the restarted game have the same player name as the saved one")
    public void doesSavedGameRestartWithSavedPLayerName() {
        log.info("doesSavedGameRestartWithSavedPLayer Test start");
        Assert.assertEquals(savedPLayerName, restartedPLayerName);
    }

    @Test(description = "does the restarted game have the same player score as the saved one")
    public void doesSavedGameRestartWithSavedPLayerScore() {
        log.info("doesSavedGameRestartWithSavedPLayerScore Test start");
        Assert.assertEquals(savedPlayerScore, restartedPlayerScore);
    }
}
