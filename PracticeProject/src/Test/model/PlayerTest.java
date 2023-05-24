package Test.model;

import Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Suying");
    }

    @Test
    void testConstructor() {
        assertEquals("Suying", testPlayer.getName());
        assertEquals(0, testPlayer.getNumCards());
        assertEquals(0, testPlayer.getNumWon());
        assertEquals(0, testPlayer.getNumLost());
        assertEquals(0, testPlayer.getNumTie());
        assertEquals(0, testPlayer.getTotalGames());
    }

    @Test
    void testAddFour() {
        testPlayer.addFour();
        assertEquals(4, testPlayer.getNumCards());
        testPlayer.addFour();
        assertEquals(8, testPlayer.getNumCards());
    }

    @Test
    void testAddTwo() {
        testPlayer.addTwo();
        assertEquals(2, testPlayer.getNumCards());
        testPlayer.addTwo();
        assertEquals(4, testPlayer.getNumCards());
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
