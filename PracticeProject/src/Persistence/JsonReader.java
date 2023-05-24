package Persistence;

import Model.*;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

//reference: JsonSerializationDemo
//Represents a reader that reads wholeGame from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads wholeGame from fil and return it
    //throws IOException if an error occurs reading data from file
    public WholeGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWholeGame(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wholeGame from JSON object and returns it
    private WholeGame parseWholeGame(JSONObject jsonObject) {
        WholeGame wg = new WholeGame();
        wg.setPlayers(readPlayers(jsonObject));
       // addCurrentGame(wg, jsonObject);
        return wg;
    }

    //MODIFIES: wholeGame
    //EFFECTS: adds currentGame to wholeGame
//    private void addCurrentGame(WholeGame wg, JSONObject jsonObject) {
//        Game currentGame = readGame(jsonObject);
//        wg.setCurrentGame(currentGame);
//    }

    //MODIFIES: wholeGame
    //EFFECTS: parses player from JSON object and add it to wholeGame
    private void addPlayers(WholeGame wg, JSONObject jsonObject) {
        HashMap<String, Player> hashMap = readPlayers(jsonObject);
        wg.setPlayers(hashMap);
    }

    //EFFECT: read players from a json file
    private HashMap<String,Player> readPlayers(JSONObject jsonObject) {
        HashMap<String, Player> hashMap = new HashMap<>();
        JSONArray jsonPlayers = jsonObject.getJSONArray("players");
//        Gson gson = new Gson();
//        HashMap<String, Player> hashMap = gson.fromJson(String.valueOf(jsonHashMap), HashMap.class);
//        return hashMap;
        for (Object player: jsonPlayers) {
            JSONObject nextPlayer = (JSONObject) player;
            Player p = readPlayer(nextPlayer);
            hashMap.put(p.getName(), p);
        }
        return hashMap;
    }

    //EFFECT: read a game from json file
//    private Game readGame(JSONObject jsonObject) {
//        JSONObject jsonone = jsonObject.getJSONObject("Player One");
//        Player playerOne = readPlayer(jsonone);
//        JSONObject jsontwo = jsonObject.getJSONObject("Player Two");
//        Player playerTwo = readPlayer(jsontwo);
//        JSONObject jsonCardDeck = jsonObject.getJSONObject("Card Deck");
//        CardDeck cardDeck = readCardDeck(jsonCardDeck);
//        JSONObject jsonWholeGame = jsonObject.getJSONObject("wholeGame");
//        WholeGame wholeGame = parseWholeGame(jsonWholeGame);
//        Game game = new Game();
//        game.setPlayerOne(playerOne);
//        game.setPlayerTwo(playerTwo);
//        game.setCardDeck(cardDeck);
//        game.setWholeGame(wholeGame);
//        return game;
//    }

    //EFFECTS: read a player from JSON file
    private Player readPlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int numCards = jsonObject.getInt("number of cards holds");
        int numWon = jsonObject.getInt("number of wins");
        int numLost = jsonObject.getInt("number of losses");
        int numTie = jsonObject.getInt("number of ties");
        int totalGames = jsonObject.getInt("number of total games");
        Player player1 = new Player(name);
        player1.setNumCards(numCards);
        player1.setNumWon(numWon);
        player1.setNumLost(numLost);
        player1.setNumTie(numTie);
        player1.setTotalGames(totalGames);
        return player1;
    }

    //EFFECT: read a card deck from json file
//    private CardDeck readCardDeck(JSONObject jsonObject) {
//        int size = jsonObject.getInt("size");
//        JSONArray jsondrawed = jsonObject.getJSONArray("list of drawed cards");
//        List<Card> drawedCards = new ArrayList<>();
//        for (Object c : jsondrawed) {
//            Card card = readCard((JSONObject) c);
//            drawedCards.add(card);
//        }
//        JSONArray jsoncards = jsonObject.getJSONArray("list of cards");
//        List<Card> cardList = new ArrayList<>();
//        for (Object c: jsoncards) {
//            Card card = readCard((JSONObject) c);
//            cardList.add(card);
//        }
//        CardDeck cardDeck = new CardDeck();
//        cardDeck.setSize(size);
//        cardDeck.setDrawed(drawedCards);
//        cardDeck.setCards(cardList);
//        return cardDeck;
//    }

    //EFFECT: read a card from json file
//    private Card readCard(JSONObject jsonObject) {
//        int rank = jsonObject.getInt("rank");
//        String suit = jsonObject.getString("suit");
//        Card card = new Card(suit, rank);
//        return card;
//    }
}
