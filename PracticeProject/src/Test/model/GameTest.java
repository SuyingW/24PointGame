package Test.model;

import Model.Exception.DuplicatePlayerException;
import Model.Exception.NoCardException;
import Model.Exception.NoPlayerException;
import Model.Game;
import Model.WholeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        assertEquals(10, testGame.getCardDeck().getCards().get(9).getRank());
        assertEquals(null, testGame.getPlayerOne());
        assertEquals(null, testGame.getPlayerTwo());
        assertEquals(52, testGame.getCardDeck().getSize());
        assertEquals(null, testGame.getWholeGame());
        assertEquals(0, testGame.getCurrentRound());
    }

    @Test
    void testAddtoWholeGame() {
        this.testWholeGame.setCurrentGame(testGame);
        assertEquals(testGame, testWholeGame.getCurrentGame());
        assertEquals(testWholeGame, testGame.getWholeGame());
    }


    @Test
    void testSetPlayerOneTwo() throws DuplicatePlayerException {
        testWholeGame.addPlayer("Suying");
        testWholeGame.addPlayer("Iris");
        testWholeGame.addPlayer("Daisy");
        this.testWholeGame.setCurrentGame(testGame);
        try {
            testGame.selectPlayerOne("Suying");
        } catch (NoPlayerException e){
            fail();
        }
        try {
            testGame.selectPlayerTwo("Suying");
        } catch (DuplicatePlayerException e) {
            //expected
        } catch (NoPlayerException e) {
            fail();
        }
        try {
            testGame.selectPlayerTwo("Iris");
        } catch (NoPlayerException e) {
            fail();
        }
        assertEquals("Suying", testGame.getPlayerOne().getName());
        assertEquals("Iris", testGame.getPlayerTwo().getName());
        assertEquals(0, testGame.getPlayerOne().getTotalGames());
        try {
            testGame.selectPlayerTwo("jason");
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
        assertEquals(48, testGame.getCardDeck().getCards().size());
        int i = 0;
        while (i <= 11) {
            testGame.selectFourCards();
            i++;
        }
        assertEquals(0, testGame.getCardDeck().getCards().size());
        try {
            testGame.selectFourCards();
        } catch (NoCardException e) {
            //expceted
        }
    }

}
