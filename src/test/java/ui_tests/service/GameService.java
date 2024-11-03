package ui_tests.service;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import ui_tests.driver.DriverSingleton;
import ui_tests.page.TetrisPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameService {
    private final Logger log = Logger.getLogger(GameService.class.getName());
    private final WebDriver driver = DriverSingleton.getDriver();
    private TetrisPage tetrisPage;
    public String savedPLayerName;
    public String savedPlayerScore;
    public String restartedPLayerName;
    public String restartedPlayerScore;

    private final String HELLO_PAGE = "http://localhost:8080/hello";
    private final String SNAPSHOT_PAGE = "http://localhost:8080/6";


    public GameService(WebDriver driver) {
        tetrisPage = new TetrisPage(driver);
    }

    public List<Pair<Integer, Integer>> getFilledCellsListSaved() {
        runScenarioBeforeGameSaving();
        List<Pair<Integer, Integer>> filledCellsListSaved = getFilledCells();
        log.info("filledCellsList saved:");
        log.info(Arrays.toString(filledCellsListSaved.toArray()));
        savedPLayerName = tetrisPage.getPlayer();
        log.info("Player Name saved:");
        log.info(savedPLayerName);
        savedPlayerScore = tetrisPage.getPlayerScore();
        log.info("Player Score saved:");
        log.info(savedPlayerScore);
        return filledCellsListSaved;
    }

    public List<Pair<Integer, Integer>> getFilledCellsListAfterRestart() {
        runScenarioAfterGameSaving();
        pauseScene();
        List<Pair<Integer, Integer>> filledCellsListAfterRestart = getFilledCells();
        log.info("filledCellsList after restart:");
        log.info(Arrays.toString(filledCellsListAfterRestart.toArray()));
        restartedPLayerName = tetrisPage.getPlayer();
        log.info("Player Name after restart:");
        log.info(restartedPLayerName);
        restartedPlayerScore = tetrisPage.getPlayerScore();
        log.info("Player Score after restart:");
        log.info(restartedPlayerScore);
        return filledCellsListAfterRestart;
    }

    private List<Pair<Integer, Integer>> getFilledCells() {
        List<Pair<Integer, Integer>> filledCellsCoordinatesList = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            for (int j = 1; j < 12; j++) {
                if (!tetrisPage.getCellValue(i, j).equals("0.png")) {
                    filledCellsCoordinatesList.add(Pair.of(i, j));
                }
            }
        }
        return getFilledCellsToCompare(filledCellsCoordinatesList);
    }

    private List<Pair<Integer, Integer>> getFilledCellsToCompare(List<Pair<Integer, Integer>> cellsList) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for (Pair<Integer, Integer> coordinate : cellsList) {
            if (coordinate.getKey() >= 10) list.add(coordinate);
        }
        return list;
    }

    private void runScenarioBeforeGameSaving() {
        tetrisPage = tetrisPage.openPage(HELLO_PAGE);
        tetrisPage = tetrisPage.startGameButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickLeftButton().clickLeftButton().clickLeftButton().clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickRightButton().clickRightButton().clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickSaveButton();
    }

    private void runScenarioAfterGameSaving() {
        tetrisPage = tetrisPage.openPage(SNAPSHOT_PAGE);
        makeUserWait(5);
        tetrisPage = tetrisPage.clickNewGameButton();
        makeUserWait(5);
        tetrisPage = tetrisPage.clickStartGameButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickRightButton().clickRightButton().clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickRestartButton();
        makeUserWait(3);

    }

    public void closeBrowser() {
        driver.close();
    }

    private void pauseScene() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("clearInterval(myTimer);");
    }

    private void makeUserWait(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
