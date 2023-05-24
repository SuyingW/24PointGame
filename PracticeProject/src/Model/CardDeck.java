package Model;

import Model.Exception.NoCardException;
import Persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {
    private List<Card> cards;
    private int size;
    private List<Card> drawed;

    //EFFECTS: construct a deck of cards with rank (1~13)
    //         suits ("Spade", "Heart", "Diamond", "Club")
    public CardDeck() {
        this.cards = new ArrayList<>();
        String[] suits = {"Spade", "Heart", "Diamond", "Club"};
        for (int i = 0; i <= 3; i++) {
            for (int j = 1; j <= 13; j++) {
                Card card = new Card(suits[i], j);
                cards.add(card);
            }
        }
        this.size = cards.size();
        this.drawed = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Card> getDrawed() {
        return drawed;
    }

    public void setDrawed(List<Card> drawed) {
        this.drawed = drawed;
    }

    public List<Card> drawFour() throws NoCardException {
        if (!drawed.isEmpty()) {
            this.drawed.clear();
        }
        if (this.cards.isEmpty()) {
            throw new NoCardException();
        } else {
            int i = 0;
            while (i <= 3) {
                Card random = this.cards.get(new Random().nextInt(this.size));
                drawed.add(random);
                cards.remove(random);
                this.size--;
                i++;
            }
        }
        return drawed;
    }

    //EFFECTS: check whether there is a solution for the drawn 4 cards
    public boolean judgePoint24() {
        double[] a = new double[]{
                (double) drawed.get(0).getRank(),
                (double) drawed.get(1).getRank(),
                (double) drawed.get(2).getRank(),
                (double) drawed.get(3).getRank()
        };
        return helper(a);
    }


    //EFFECTS: check if the given array can evaluate to 24
    private boolean helper(double[] a) {
        if(a.length==1) return Math.abs(a[0]-24)<0.0001;

        for(int i=0; i<a.length; i++) {
            for(int j=i+1; j<a.length; j++) {
                double[] b = new double[a.length-1];

                for(int k=0, index=0; k<a.length; k++) {
                    if(k != i && k != j) {
                        b[index++] = a[k];
                    }
                }

                for(double d: compute(a[i], a[j])) {
                    b[b.length-1] = d;
                    if(helper(b)) return true;
                }
            }
        }
        return false;
    }

    //EFFECTS: return all the combinations using arithmetics on two doubles
    private double[] compute(double x, double y) {
        return new double[]{x+y, x-y, y-x, x*y, x/y, y/x};
    }

//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("list of cards", cardsToJson());
//        json.put("size", size);
//        json.put("list of drawed cards", drawedToJson());
//        return json;
//    }
//
//    //EFFECTS: returns cards in this cardDeck as a JSON array
//    private JSONArray cardsToJson() {
//        JSONArray jsonArray = new JSONArray();
//        for(Card c: cards) {
//            jsonArray.put(c.toJson());
//        }
//        return jsonArray;
//    }

    //EFFECTS: returns cards in drawed as a JSON array
//    private JSONArray drawedToJson() {
//        JSONArray jsonArray = new JSONArray();
//        for(Card c: drawed) {
//            jsonArray.put(c.toJson());
//        }
//        return jsonArray;
//    }

}

