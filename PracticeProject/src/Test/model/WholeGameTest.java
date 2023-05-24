package Test.model;

import Model.Exception.DuplicatePlayerException;
import Model.Game;
import Model.Player;
import Model.WholeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WholeGameTest {
    private WholeGame testWholeGame;
    private Game testGame;

    @BeforeEach
    void runBefore() {
        this.testWholeGame = new WholeGame();
        this.testGame = new Game();
    }


    @Test
    void testAddPlayer() throws DuplicatePlayerException {
        testWholeGame.addPlayer("Suying");
        testWholeGame.addPlayer("Iris");
        assertEquals(2, testWholeGame.getPlayers().size());
        assertTrue(testWholeGame.getPlayers().containsKey("Suying"));
        Player iris = testWholeGame.getPlayers().get("Iris");
        assertEquals("Iris", iris.getName());
        assertEquals(0, iris.getTotalGames());
        try {
            testWholeGame.addPlayer("Iris");
        } catch (DuplicatePlayerException e) {
            //expected
        }
        assertEquals(2, testWholeGame.getPlayers().size());
    }

    @Test
    void testRemovePlayer() throws DuplicatePlayerException {
        testWholeGame.addPlayer("Iris");
        testWholeGame.addPlayer("Daisy");
        testWholeGame.removePlayer("Suying");
        assertEquals(2, testWholeGame.getPlayers().size());
        testWholeGame.removePlayer("Iris");
        assertEquals(1, testWholeGame.getPlayers().size());
    }

    @Test
    void testSetCurrentGame() {
        this.testWholeGame.setCurrentGame(this.testGame);
        assertEquals(testGame, testWholeGame.getCurrentGame());
        assertEquals(testWholeGame, testGame.getWholeGame());
    }

}
