package Test.model;

import Model.CardDeck;
import Model.Exception.NoCardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckTest {
    private CardDeck testCardDeck;

    @BeforeEach
    void runBefore() {
        testCardDeck = new CardDeck();
    }

    @Test
    void testConstructor() {
        assertEquals("Spade", testCardDeck.getCards().get(0).getSuit());
        assertEquals(1, testCardDeck.getCards().get(0).getRank());
        assertEquals("Heart", testCardDeck.getCards().get(13).getSuit());
        assertEquals(2, testCardDeck.getCards().get(14).getRank());
        assertEquals(52, testCardDeck.getSize());
    }

    @Test
    void testDrawFour() throws NoCardException {
        try {
            testCardDeck.drawFour();
        } catch (NoCardException e){
            fail();
        }
        assertEquals(48,testCardDeck.getSize());
        assertEquals(4, testCardDeck.getDrawed().size());
        assertEquals(48, testCardDeck.getCards().size());
        testCardDeck.drawFour();
        assertEquals(44,testCardDeck.getSize());
        assertEquals(4, testCardDeck.getDrawed().size());
        assertEquals(44, testCardDeck.getCards().size());
        int i = 0;
        while (i <= 10) {
            testCardDeck.drawFour();
            i++;
        }
        assertEquals(0,testCardDeck.getSize());
        assertEquals(4, testCardDeck.getDrawed().size());
        assertEquals(0, testCardDeck.getCards().size());
        try {
            testCardDeck.drawFour();
        } catch (NoCardException e) {
            //expected
        }
    }

    @Test
    void testJudgePoint24() throws NoCardException {
        testCardDeck.drawFour();
        assertTrue(testCardDeck.judgePoint24());
    }

}
