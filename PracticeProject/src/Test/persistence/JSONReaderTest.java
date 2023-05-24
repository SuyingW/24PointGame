package Test.persistence;

import Model.WholeGame;
import Persistence.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JSONReaderTest extends JSONTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WholeGame wg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyWholeGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWholeGame.json");
        try {
            WholeGame wg = reader.read();
            assertEquals(0, wg.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWholeGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWholeGame.json");
        try {
            WholeGame wg = reader.read();
            assertEquals(2, wg.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

 }
