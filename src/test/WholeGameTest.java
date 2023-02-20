package test;

import Models.Exceptions.DuplicatePlayerException;
import Models.Game;
import Models.Player;
import Models.WholeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        testWholeGame.addPlayer(new Player("Suying"));
        testWholeGame.addPlayer(new Player("Iris"));
        assertEquals(2, testWholeGame.getPlayers().size());
        assertEquals("Suying", testWholeGame.getPlayers().get(0).getName());
        assertEquals("Iris", testWholeGame.getPlayers().get(1).getName());
        assertEquals(0, testWholeGame.getPlayers().get(0).getTotalGames());
        assertEquals(0, testWholeGame.getPlayers().get(1).getCards().size());
        try {
            testWholeGame.addPlayer(new Player("Iris"));
        } catch (DuplicatePlayerException e) {
            //expected
        }
        assertEquals(2, testWholeGame.getPlayers().size());
    }


    @Test
    void testRemovePlayer() throws DuplicatePlayerException {
        Player Suying = new Player("Suying");
        Player Iris = new Player("Iris");
        Player Daisy = new Player("Daisy");
        testWholeGame.addPlayer(Suying);
        testWholeGame.addPlayer(Iris);
        testWholeGame.addPlayer(Daisy);
        testWholeGame.removePlayer(Suying);
        assertEquals(2, testWholeGame.getPlayers().size());
        testWholeGame.removePlayer(Iris);
        assertEquals(1, testWholeGame.getPlayers().size());
    }

    @Test
    void testSetCurrentGame() {
        this.testWholeGame.setCurrentGame(this.testGame);
        assertEquals(testGame, testWholeGame.getCurrentGame());
        assertEquals(testWholeGame, testGame.getWholeGame());
    }

}
