package com.app.game.tetris.model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

@Component
@Scope("prototype")
public class SavedGame implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @BsonProperty("playerName")
    private final String playerName;

    @BsonProperty("playerScore")
    private final int playerScore;

    @BsonProperty("cells")
    private final char[][] cells;



    @BsonCreator
    public SavedGame( @BsonProperty("playerName") String playerName,  @BsonProperty("playerScore") int playerScore,  @BsonProperty("cells") char[][] cells) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.cells = cells;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public char[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "SavedGame{" +
                "playerName='" + playerName + '\'' +
                ", playerScore=" + playerScore +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }
}
