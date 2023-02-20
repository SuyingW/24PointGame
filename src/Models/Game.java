package Models;

import Models.Exceptions.DuplicatePlayerException;
import Models.Exceptions.NoCardException;
import Models.Exceptions.NoPlayerException;

import java.util.ArrayList;
import java.util.List;

import static sun.misc.Version.print;

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

    //EFFECTS: return the list of card in card deck
    public List<Card> getCardList() {
        return this.cardDeck.getCards();
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    public CardDeck getCardDeck() {
        return this.cardDeck;
    }

    public WholeGame getWholeGame() {
        return this.wholeGame;
    }

    public int getCurrentRound() {
        return this.currentRound;
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

    //REQUIRES: name is from a player in the player list
    //MODIFIES: this
    //EFFECTS: set playerOne to be the given name
    public void setPlayerOne(String name) throws NoPlayerException {
        Boolean exist = false;
        for (Player p : this.wholeGame.getPlayers()) {
            if (p.getName().equals(name)) {
                exist = true;
                this.playerOne = p;
            }
        }
        if(!exist) {
            throw new NoPlayerException();
        }
    }

    //REQUIRES: name is from a player in the player list and is not the same as playerOne's
    //MODIFIES: this
    //EFFECTS: set playerOne to be the given name
    public void setPlayerTwo(String name) throws NoPlayerException, DuplicatePlayerException {
        Boolean duplicate = false;
        Boolean exist = false;
        for (Player p : this.wholeGame.getPlayers()) {
            if (p.getName().equals(name) ) {
                exist = true;
                if(this.playerOne.getName().equals(p.getName())) {
                    duplicate = true;
                } else {
                    this.playerTwo = p;
                }
            }
        }
        if(!exist) {
            throw new NoPlayerException();
        }
        if(duplicate) {
            throw new DuplicatePlayerException();
        }
    }

    //REQUIRES: the card deck is not empty
    //MODIFIES: this
    //EFFECTS: if the card deck is empty, throw noCardException, otherwise randomly select four cards
    public void selectFourCards() throws NoCardException {
            this.cardDeck.drawFour();
    }
}
