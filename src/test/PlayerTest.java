package test;

import Models.Card;
import Models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    private Player testPlayer;
    private List testCards;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Suying");
        testCards = testPlayer.getCards();
    }

    @Test
    void testConstructor() {
        assertEquals("Suying", testPlayer.getName());
        assertTrue(testCards.isEmpty());
        assertEquals(0, testPlayer.getNumCards());
        assertEquals(0, testPlayer.getNumWon());
        assertEquals(0, testPlayer.getNumLost());
        assertEquals(0, testPlayer.getNumTie());
        assertEquals(0, testPlayer.getTotalGames());
    }

    @Test
    void testAddFour() {
        List<Card> fourCards = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Card card = new Card("Diamonds", i);
            fourCards.add(card);
        }
        testPlayer.addFour(fourCards);
        assertEquals(4, testPlayer.getNumCards());
        assertEquals(fourCards, testPlayer.getCards());
    }

    @Test
    void testAddTwo() {
        List<Card> twoCards = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Card card = new Card("Diamonds", i);
            twoCards.add(card);
        }
        testPlayer.addTwo(twoCards);
        assertEquals(2, testPlayer.getNumCards());
        assertEquals(twoCards, testPlayer.getCards());
    }

    @Test
    void testWinLostTie() {
        testPlayer.win();
        testPlayer.lose();
        testPlayer.tie();
        testPlayer.tie();
        assertEquals(1, testPlayer.getNumWon());
        assertEquals(1, testPlayer.getNumLost());
        assertEquals(2, testPlayer.getNumTie());
        assertEquals(4, testPlayer.getTotalGames());
    }

}
