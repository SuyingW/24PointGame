package Model;

import Persistence.Writable;
import org.json.JSONObject;

public class Player implements Writable {

    private String name;

    private int numCards;

    private int numWon;

    private int numLost;

    private int numTie;

    private int totalGames;

    //EFFECTS: construct a player with no cards and game records
    public Player(String name) {
        this.name = name;
        this.numCards = 0;
        this.numWon = 0;
        this.numLost = 0;
        this.numTie = 0;
        this.totalGames = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public int getNumWon() {
        return numWon;
    }

    public void setNumWon(int numWon) {
        this.numWon = numWon;
    }

    public int getNumLost() {
        return numLost;
    }

    public void setNumLost(int numLost) {
        this.numLost = numLost;
    }

    public int getNumTie() {
        return numTie;
    }

    public void setNumTie(int numTie) {
        this.numTie = numTie;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    // EFFECTS: set the number of card the player holds to 0
    public void setNumCardsZero() {
        this.numCards = 0;
    }

    //EFFECTS: numCards + 4
    public void addFour() {
        this.numCards = this.numCards + 4;
    }

    //EFFECTS: numCards + 2
    public void addTwo() {
        this.numCards = this.numCards + 2;
    }

    //MODIFIES: this
    //EFFECTS: update number of victory and total number of games participated in
    public void win() {
        this.numWon++;
        this.totalGames++;
        model.EventLog.getInstance().logEvent(new Event(name + " won the game"));
    }

    //MODIFIES: this
    //EFFECTS: update number of losses and total number of games participated in
    public void lose() {
        this.numLost++;
        this.totalGames++;
        model.EventLog.getInstance().logEvent(new Event(name + " lost the game"));
    }

    //MODIFIES: this
    //EFFECTS: update number of ties and total number of games participated in
    public void tie() {
        this.numTie++;
        this.totalGames++;
        model.EventLog.getInstance().logEvent(new Event( "Tied game"));
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("number of cards holds", numCards);
        json.put("number of wins", numWon);
        json.put("number of losses", numLost);
        json.put("number of ties", numTie);
        json.put("number of total games", totalGames);
        return json;
    }

}
