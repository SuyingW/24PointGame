package Model;

import Persistence.Writable;
import org.json.JSONObject;

public class Card {
    private int rank;
    private String suit;


    //EFFECT: constructs a card with given suit and rank
    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("rank", rank);
//        json.put("suit", suit);
//        return json;
//    }

}
