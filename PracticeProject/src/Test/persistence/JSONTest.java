package Test.persistence;

import Model.Game;
import Model.Player;
import Model.WholeGame;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONTest {
    protected void checkWholeGame(HashMap<String, Player> players, Game currentGame, WholeGame wholeGame) {
        assertEquals(players, wholeGame.getPlayers());
        assertEquals(currentGame, wholeGame.getCurrentGame());
    }
}
