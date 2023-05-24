package Model;


import Model.Exception.DuplicatePlayerException;
import Model.Exception.NoCardException;
import Model.Exception.NoPlayerException;
import Persistence.Writable;
import org.json.JSONObject;
import ui.HomePage;

import java.util.HashMap;

//Represents a game that contains a list of players, two players participating in the game, and a deck of cards
public class Game {
    private Player playerOne;
    private Player playerTwo;
    private CardDeck cardDeck;
    private int currentRound;
    private WholeGame wholeGame;

    //EFFECTS: constructs a game with no players and a deck of 52 cards
    public Game() {
        this.playerOne = null;
        this.playerTwo = null;
        this.cardDeck = new CardDeck();
        this.wholeGame = null;
        currentRound = 0;
    }


    public Player getPlayerOne() {
        return playerOne;
    }

//    public void setPlayerOne(Player playerOne) {
//        this.playerOne = playerOne;
//    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

//    public void setPlayerTwo(Player playerTwo) {
//        this.playerTwo = playerTwo;
//    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(CardDeck cardDeck) {
        this.cardDeck = cardDeck;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public WholeGame getWholeGame() {
        return wholeGame;
    }

    public void setWholeGame(WholeGame wholeGame) {
        this.wholeGame = wholeGame;
    }

    public void incrementCurrentRound() {
        this.currentRound++;
    }

    //EFFECTS: add whole game to this game and set this game as current game
    public void addtoWholeGame(WholeGame wg) {
        if (this.wholeGame != wg) {
            this.wholeGame = wg;
            wg.setCurrentGame(this);
        }
    }

    public void setPlayerOne(Player player) {
        this.playerOne = player;
    }

    public void setPlayerTwo(Player player) {
        this.playerTwo = player;
    }

    //REQUIRES: name is from a player in the player list
    //MODIFIES: this
    //EFFECTS: set playerOne to be the given name
    public void selectPlayerOne(String name) throws NoPlayerException {
        HashMap<String, Player> players = this.wholeGame.getPlayers();
        if(players.containsKey(name)) {
            this.playerOne = players.get(name);
        } else {
            throw new NoPlayerException();
        }
        model.EventLog.getInstance().logEvent(new Event(name + " started a game"));
    }

    //REQUIRES: name of a player in the hashmap and is not the same as playerOne's
    //MODIFIES: this
    //EFFECTS: set playerTwo to be the given name
    public void selectPlayerTwo(String name) throws NoPlayerException, DuplicatePlayerException {

        HashMap<String, Player> players = this.wholeGame.getPlayers();

        if (playerOne.getName().equals(name)) {
            throw new DuplicatePlayerException();
        } else if (players.containsKey(name)) {
            this.playerTwo = players.get(name);
        } else {
            throw new NoPlayerException();
        }
        model.EventLog.getInstance().logEvent(new Event(name + " started a game"));
    }

    //REQUIRES: the card deck is not empty
    //MODIFIES: this
    //EFFECTS: if the card deck is empty, throw noCardException, otherwise randomly select four cards
    public void selectFourCards() throws NoCardException {
        this.cardDeck.drawFour();
    }

    public void killGame() {
        playerOne.setNumCards(0);
        playerTwo.setNumCards(0);
        setPlayerOne(null);
        setPlayerTwo(null);
        setCardDeck(new CardDeck());
        setCurrentRound(0);
    }

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("Player One", playerOne.toJson());
//        json.put("Player Two", playerTwo.toJson());
//        json.put("Card Deck", cardDeck.toJson());
//        json.put("Current round", currentRound);
//        json.put("wholeGame", wholeGame.toJson());
//        return json;
//    }

}
