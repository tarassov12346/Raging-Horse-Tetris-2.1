package ui_tests.page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TetrisPage extends AbstractPage {

    @FindBy(id = "newGameButton")
    private WebElement newGameButton;

    @FindBy(id = "leftButton")
    private WebElement leftButton;

    @FindBy(id = "rightButton")
    private WebElement rightButton;

    @FindBy(id = "dropButton")
    private WebElement dropButton;

    @FindBy(id = "saveButton")
    private WebElement saveButton;

    @FindBy(id = "restartButton")
    private WebElement restartButton;

    @FindBy(id = "gameStatusBox")
    private WebElement gameStatusBox;

    @FindBy(id = "playerBox")
    private WebElement playerBox;

    @FindBy(id = "playerScoreBox")
    private WebElement playerScoreBox;

    @FindBy(id = "bestPlayerBox")
    private WebElement bestPlayerBox;

    @FindBy(id = "bestPlayerScoreBox")
    private WebElement bestPlayerScoreBox;

    @FindBy(id = "table")
    private WebElement table;

    @FindBy(id = "startGameButton")
    private WebElement startGameButton;

    public TetrisPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public TetrisPage openPage(String url) {
        driver.navigate().to(url);
        return this;
    }

    public TetrisPage startGameButton() {
        startGameButton.click();
        return this;
    }

    public TetrisPage clickNewGameButton() {
        newGameButton.click();
        return this;
    }

    public TetrisPage clickStartGameButton() {
        startGameButton.click();
        return this;
    }

    public TetrisPage clickLeftButton() {
        leftButton.click();
        return this;
    }

    public TetrisPage clickRightButton() {
        rightButton.click();
        return this;
    }

    public TetrisPage clickDropButton() {
        dropButton.click();
        return this;
    }

    public TetrisPage clickSaveButton() {
        saveButton.click();
        return this;
    }

    public TetrisPage clickRestartButton() {
        restartButton.click();
        return this;
    }

    public String getGameStatus() {
        return gameStatusBox.getText();
    }

    public String getPlayer() {
        return playerBox.getText();
    }

    public String getPlayerScore() {
        return playerScoreBox.getText();
    }

    public String getBestPlayer() {
        return bestPlayerBox.getText();
    }

    public String getBestPlayerScore() {
        return bestPlayerScoreBox.getText();
    }

    public String getCellValue(int i, int j) {
        return StringUtils.right(driver.findElement(By.id("c" + i + "v" + j)).getAttribute("src"), 5);
    }

    public WebElement getRestartButton() {
        return restartButton;
    }

    public WebElement getStartGameButton() {
        return startGameButton;
    }

    public WebElement getNewGameButton() {
        return newGameButton;
    }
}
