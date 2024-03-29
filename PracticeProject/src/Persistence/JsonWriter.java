package Persistence;

import Model.WholeGame;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private PrintWriter writer;

    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of WholeGame to file
    public void write(WholeGame wg) {
        JSONObject json = wg.toJson();
        saveToFile(json.toString());
    }

    //MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    //MODIFIES: this
    //EFFECTS: close writer
    public void close() {
        writer.close();
    }
}
