package ui_tests.test;

import com.app.game.tetris.TetrisNewApplication;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ui_tests.driver.DriverSingleton;
import ui_tests.service.GameService;

import java.util.List;

public class CommonConditions {
    protected static final Logger log = Logger.getLogger(GameSaveTest.class);

    public WebDriver driver;
    List<Pair<Integer, Integer>> savedGameCells;
    List<Pair<Integer, Integer>> restartedGameCells;
    String savedPLayerName;
    String savedPlayerScore;
    String restartedPLayerName;
    String restartedPlayerScore;
    GameService gameService;

    ConfigurableApplicationContext ctx;

    @BeforeClass
    public void doBeforeTests() {
        ctx = SpringApplication.run(TetrisNewApplication.class);
        log.info("UITests start");
        driver = DriverSingleton.getDriver();
        gameService = new GameService(driver);
        savedGameCells = gameService.getFilledCellsListSaved();
        restartedGameCells = gameService.getFilledCellsListAfterRestart();
        savedPLayerName = gameService.savedPLayerName;
        savedPlayerScore = gameService.savedPlayerScore;
        restartedPLayerName = gameService.restartedPLayerName;
        restartedPlayerScore = gameService.restartedPlayerScore;
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    @AfterMethod
    public void doAfterEachTestMethod() {
        log.info("Test Method  is finished");
    }

    @AfterClass
    public void doAfterTests() {
        log.info("UITests are finished");
        ctx.close();
    }
}
