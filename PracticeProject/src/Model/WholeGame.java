package Model;

import Model.Exception.DuplicatePlayerException;
import Persistence.Writable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WholeGame implements Writable {

    private HashMap<String, Player> players;
    private Game currentGame;

    public WholeGame() {
        this.players = new HashMap<String, Player>();
        this.currentGame = new Game();
        this.currentGame.addtoWholeGame(this);
    }

    //REQUIRES: name
    //MODIFIES: this
    //EFFECTS: create a player with the given name and add the player to the game
    public void addPlayer(String name) throws DuplicatePlayerException {

        Boolean duplicate = false;

        if (players.containsKey(name)) {
            duplicate = true;
            throw new DuplicatePlayerException();
        }

        if (!duplicate) {
            Player p = new Player(name);
            players.put(name, p);
        }
        model.EventLog.getInstance().logEvent(new Event(name + " was added to system"));
    }

    //REQUIRES: the given player is in hashmap
    //MODIFIES: this
    //EFFECTS: remove the player with the given name
    public void removePlayer(String name) {
        if (players.containsKey(name)) {
            players.remove(name);
        } else {
            System.out.print("User doesn't exist");
        }
    }


    public HashMap<String, Player> getPlayers() {
        return players;
    }
    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    //EFFECTS: set current game and assign the whole game to the current game
    public void setCurrentGame(Game g) {
        if (this.currentGame != g) {
            this.currentGame = g;
            g.addtoWholeGame(this);
        }
    }

    @Override
    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        ObjectMapper objectMapper = new ObjectMapper();
//        //json.put("currentGame", currentGame.toJson());
////        json.put("players", playersToJson("playersHashMap", players));
//        try {
//            objectMapper.writeValue(new File("output.json"), players);
//            System.out.println("HashMap successfully written to output.json");
//    } catch (IOException e) {
//            e.printStackTrace();
//        }


//    public void playersToJson(String fileName, HashMap<String, Player> players) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValue(new File(fileName), players);
//    }
        JSONObject json = new JSONObject();
        json.put("players", playersToJson());
        return json;
    }

    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        Iterator<Map.Entry<String, Player>> iterator = players.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Player> entry = iterator.next();
            Player player = entry.getValue();
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }

}