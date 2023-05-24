package Test.model;

import Model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    private Card testCard;
    @BeforeEach
    void runBefore() {
        testCard = new Card("Diamonds", 13);
    }

    @Test
    void testConstructor() {
        assertEquals("Diamonds", testCard.getSuit());
        assertEquals(13, testCard.getRank());
    }


}
