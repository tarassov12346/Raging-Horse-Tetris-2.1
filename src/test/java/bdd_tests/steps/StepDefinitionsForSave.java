package bdd_tests.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui_tests.driver.DriverSingleton;
import ui_tests.page.TetrisPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StepDefinitionsForSave {
    private final Logger log = Logger.getLogger(StepDefinitionsForSave.class.getName());
    private WebDriver driver;
    private TetrisPage tetrisPage;
    private List<Pair<Integer, Integer>> filledCellsListSaved;
    private List<Pair<Integer, Integer>> filledCellsListAfterRestart;
    private String savedPLayerName;
    private String savedPlayerScore;
    private String restartedPLayerName;
    private String restartedPlayerScore;

    private final String HELLO_PAGE = "http://Dunny:dun@localhost:8080/hello";
    private final String SNAPSHOT_PAGE = "http://localhost:8080/6";

    @Given("I open the game page thru {string}")
    public void iOpenTheGamePageThru(String browser) {
        System.setProperty("browser", browser);
        driver = DriverSingleton.getDriver();
        tetrisPage = new TetrisPage(driver);
        tetrisPage = tetrisPage.openPage(HELLO_PAGE);
        tetrisPage = tetrisPage.clickStartGameButton();
    }

    @And("player waits {int} seconds")
    public void playerWaitsSeconds(int seconds) {
        makeUserWait(seconds);
    }

    @And("player presses 3 times Left button and Drop button and player waits {int} seconds")
    public void playerPressesTimesLeftButtonAndDropButton(int seconds) {
        tetrisPage = tetrisPage.clickLeftButton().clickLeftButton().clickLeftButton().clickDropButton();
        makeUserWait(seconds);
    }

    @And("player presses 2 times Right button and Drop button and player waits {int} seconds")
    public void playerPressesTimesRightButtonAndDropButton(int seconds) {
        tetrisPage = tetrisPage.clickRightButton().clickRightButton().clickDropButton();
        makeUserWait(seconds);
    }

    @And("player presses Drop button and player waits {int} seconds")
    public void playerPressesDropButton(int seconds) {
        tetrisPage = tetrisPage.clickDropButton();
        makeUserWait(seconds);
    }

    @And("player presses Save button")
    public void playerPressesSaveButton() {
        tetrisPage = tetrisPage.clickSaveButton();
        filledCellsListSaved = getFilledCells();
        log.info("filledCellsList saved:");
        log.info(Arrays.toString(filledCellsListSaved.toArray()));
        savedPLayerName = tetrisPage.getPlayer();
        log.info("Player Name saved:");
        log.info(savedPLayerName);
        savedPlayerScore = tetrisPage.getPlayerScore();
        log.info("Player Score saved:");
        log.info(savedPlayerScore);
    }

    @And("player again opens the game page and player waits {int} seconds")
    public void playerAgainOpensTheGamePage(int seconds) {
        tetrisPage = tetrisPage.openPage(SNAPSHOT_PAGE);
        makeUserWait(5);
        tetrisPage = tetrisPage.clickNewGameButton();
        makeUserWait(5);
        tetrisPage = tetrisPage.startGameButton();
        makeUserWait(seconds);
    }

    @And("player again presses 2 times Right button and Drop button and player waits {int} seconds")
    public void playerAgainPressesTimesRightButtonAndDropButtonAndPlayerWaitsSeconds(int seconds) {
        tetrisPage = tetrisPage.clickRightButton().clickRightButton().clickDropButton();
        makeUserWait(seconds);
    }

    @And("player again presses Drop button and player waits {int} seconds")
    public void playerAgainPressesDropButtonAndPlayerWaitsSeconds(int seconds) {
        tetrisPage = tetrisPage.clickDropButton();
        makeUserWait(seconds);
    }

    @When("player presses Restart button")
    public void playerPressesRestartButton() {
        tetrisPage = tetrisPage.clickRestartButton();
        makeUserWait(3);
        pauseScene();
        filledCellsListAfterRestart = getFilledCells();
        log.info("filledCellsList after restart:");
        log.info(Arrays.toString(filledCellsListAfterRestart.toArray()));
        restartedPLayerName = tetrisPage.getPlayer();
        log.info("Player Name after restart:");
        log.info(restartedPLayerName);
        restartedPlayerScore = tetrisPage.getPlayerScore();
        log.info("Player Score after restart:");
        log.info(restartedPlayerScore);
    }

    @Then("mosaic of fallen tetraminos changes into that as it was when Save button was pressed during previous game")
    public void mosaicOfFallenTetraminosChangesIntoThatAsItWasWhenSaveButtonWasPressedDuringPreviousGame() {
        Assert.assertEquals(filledCellsListSaved, filledCellsListAfterRestart);
    }

    @And("the restarted game have the same player name as the saved one")
    public void theRestartedGameHaveTheSamePlayerNameAsTheSavedOne() {
        Assert.assertEquals(savedPLayerName, restartedPLayerName);
    }

    @And("the restarted game have the same player score as the saved one")
    public void theRestartedGameHaveTheSamePlayerScoreAsTheSavedOne() {
        Assert.assertEquals(savedPlayerScore, restartedPlayerScore);
    }

    private void makeUserWait(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Pair<Integer, Integer>> getFilledCellsToCompare(List<Pair<Integer, Integer>> cellsList) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for (Pair<Integer, Integer> coordinate : cellsList) {
            if (coordinate.getKey() >= 10) list.add(coordinate);
        }
        return list;
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

    private void pauseScene() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("clearInterval(myTimer);");
    }
}
