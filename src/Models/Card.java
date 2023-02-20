package Models;

//Represents a card that has a suit and a rank
public class Card {
    private int rank;
    private String suit;

    //EFFECT: contructs a card with given suit and rank
    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }
}
