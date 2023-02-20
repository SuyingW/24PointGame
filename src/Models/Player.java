package Models;

import java.util.ArrayList;
import java.util.List;

//Represents a player that contains a list of cards, number of cards, and records of wins, loss, ties, total games
public class Player {
    private String name;
    private int numCards;
    private List<Card> cards;
    private int numWon;
    private int numLost;
    private int numTie;
    private int totalGames;

    //EFFECTS: construct a player with no cards and game records
    public Player(String name) {
        this.name = name;
        this.numCards = 0;
        this.cards = new ArrayList<>();
        this.numWon = 0;
        this.numLost = 0;
        this.numTie = 0;
        this.totalGames = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getNumCards() {
        return this.numCards;
    }

    public int getNumWon() {
        return this.numWon;
    }

    public int getNumLost() {
        return this.numLost;
    }

    public int getNumTie() {
        return this.numTie;
    }

    public int getTotalGames() {
        return this.totalGames;
    }


    // EFFECTS: set the number of card the player holds to 0
    public void setNumCardsZero() {
        this.numCards = 0;
    }

    public List<Card> getCards() {
        return cards;
    }

    //REQUIRES: a list of 4 cards
    //MODIFIES: this
    //EFFECTS: add four cards to the player's list of cards,
    //         update number of cards
    public void addFour(List<Card> fourCards) {
        for (Card c : fourCards) {
            this.cards.add(c);
        }
        this.numCards = this.numCards + 4;
    }

    //REQUIRES: a list of 2 cards
    //MODIFIES: this
    //EFFECTS: add two cards to the player's list of cards,
    //         update number of cards
    public void addTwo(List<Card> twoCards) {
        for (Card c : twoCards) {
            this.cards.add(c);
        }
        this.numCards = this.numCards + 2;
    }

    //REQUIRES: none
    //MODIFIES: this
    //EFFECTS: update number of victory and total number of games participated in
    public void win() {
        this.numWon++;
        this.totalGames++;
    }

    //REQUIRES: none
    //MODIFIES: this
    //EFFECTS: update number of losses and total number of games participated in
    public void lose() {
        this.numLost++;
        this.totalGames++;
    }

    //REQUIRES: none
    //MODIFIES: this
    //EFFECTS: update number of ties and total number of games participated in
    public void tie() {
        this.numTie++;
        this.totalGames++;
    }
}
