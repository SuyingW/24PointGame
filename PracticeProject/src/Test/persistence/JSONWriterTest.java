package Test.persistence;

import Model.Exception.DuplicatePlayerException;
import Model.Game;
import Model.WholeGame;
import Persistence.JsonReader;
import Persistence.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JSONWriterTest extends JSONTest{

    @Test
    void testWriterInvalidFile() {
        try {
            WholeGame wholeGame = new WholeGame();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyWholeGame() {
        try {
            WholeGame wg = new WholeGame();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWholeGame.json");
            writer.open();
            writer.write(wg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWholeGame.json");
            wg = reader.read();
            assertEquals(0, wg.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWholeGame() {
        try {
            WholeGame wg = new WholeGame();
            wg.addPlayer("Suying");
            wg.addPlayer("Iris");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWholeGame.json");
            writer.open();
            writer.write(wg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWholeGame.json");
            wg = reader.read();
            assertEquals("Suying", wg.getPlayers().get("Suying").getName());
            //assertEquals(0, wg.getCurrentGame().getPlayerTwo().getTotalGames());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (DuplicatePlayerException e) {
            fail("Exception should not have been thrown");
        }
    }


}
