package Models;

import Models.Exceptions.NoCardException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Represents a deck of card
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
        return this.cards;
    }

    public int getSize() {
        return this.size;
    }

    public List<Card> getDrawed() {
        return this.drawed;
    }

    //REQUIRES: the card deck is not empty
    //MODIFIES: this
    //EFFECTS: select four random cards from card deck and add to a new list,
    //         update the number of cards in card deck
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
}
