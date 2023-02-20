package test;

import Models.Exceptions.DuplicatePlayerException;
import Models.Exceptions.NoCardException;
import Models.Exceptions.NoPlayerException;
import Models.Game;
import Models.Player;
import Models.WholeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game testGame;
    private WholeGame testWholeGame;

    @BeforeEach
    void runBefore() {
        this.testGame = new Game();
        this.testWholeGame = new WholeGame();
    }

    @Test
    void testConstructor() {
        assertEquals(10, testGame.getCardList().get(9).getRank());
        assertEquals(null, testGame.getPlayerOne());
        assertEquals(null, testGame.getPlayerTwo());
        assertEquals(52, testGame.getCardDeck().getSize());
    }

    @Test
    void testAddtoWholeGame() {
        this.testWholeGame.setCurrentGame(testGame);
        assertEquals(testGame, testWholeGame.getCurrentGame());
        assertEquals(testWholeGame, testGame.getWholeGame());
    }


    @Test
    void testSetPlayerOneTwo() throws DuplicatePlayerException {
        Player Suying = new Player("Suying");
        Player Iris = new Player("Iris");
        Player Daisy = new Player("Daisy");
        testWholeGame.addPlayer(Suying);
        testWholeGame.addPlayer(Iris);
        testWholeGame.addPlayer(Daisy);
        this.testWholeGame.setCurrentGame(testGame);
        try {
            testGame.setPlayerOne("Suying");
        } catch (NoPlayerException e){
            fail();
        }
        try {
            testGame.setPlayerTwo("Suying");
        } catch (DuplicatePlayerException e) {
            //expected
        } catch (NoPlayerException e) {
            fail();
        }
        try {
            testGame.setPlayerTwo("Iris");
        } catch (NoPlayerException e) {
            fail();
        }
        assertEquals("Suying", testGame.getPlayerOne().getName());
        assertEquals("Iris", testGame.getPlayerTwo().getName());
        assertEquals(0, testGame.getPlayerOne().getTotalGames());
        try {
            testGame.setPlayerTwo("jason");
        } catch (NoPlayerException e) {
            //expected
        }

    }

    @Test
    void testSelectFourCards() throws NoCardException {
        try {
            testGame.selectFourCards();
        } catch (NoCardException e) {
            fail();
        }
        assertEquals(4, testGame.getCardDeck().getDrawed().size());
        assertEquals(48, testGame.getCardList().size());
        int i = 0;
        while (i <= 11) {
            testGame.selectFourCards();
            i++;
        }
        assertEquals(0, testGame.getCardList().size());
        try {
            testGame.selectFourCards();
        } catch (NoCardException e) {
            //expceted
        }
    }
}



