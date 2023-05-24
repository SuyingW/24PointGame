package ui;

import Model.Card;
import Model.Exception.NoCardException;
import Model.Game;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Draw {
    protected JFrame frame;
    protected JPanel panel;
    protected JButton draw;
    protected JLabel cardOneRank;
    protected JLabel cardOneSuit;
    protected  JLabel cardTwoRank;
    protected JLabel cardTwoSuit;
    protected JLabel cardThreeRank;
    protected JLabel cardThreeSuit;
    protected JLabel cardFourRank;
    protected JLabel cardFourSuit;
    protected JButton solution;
    protected JLabel showSolution;
    protected JLabel scoreOne;
    protected JLabel scoreTwo;
    protected JLabel round;

    public Draw() {
        frame = new JFrame();
        frame.setSize(1500,1000);
        panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        frame.setVisible(true);
        displayCardBackground();
        displayRound();
        drawCards();
        solution();
        displayScore();
        playerOne();
        playerTwo();
        tie();
        quit();
    }

    public void drawCards() {
        draw = new JButton("Draw Cards");
        draw.setBounds(1320,50,100,25);
        panel.add(draw);
        Game currentGame = HomePage.wholeGame.getCurrentGame();
        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currentGame.selectFourCards();
                    currentGame.incrementCurrentRound();
                    int roundNum = currentGame.getCurrentRound();
                    List<Card> drawed = currentGame.getCardDeck().getDrawed();
                    displayCards(drawed);

                    round.setText("Round " + roundNum);
                    if(roundNum==13) {
                        draw.setText("Show Result");
                    }
                } catch (NoCardException ex) {
                    new Result();
                    currentGame.killGame();
                    currentGame.setWholeGame(HomePage.wholeGame);
                    frame.setVisible(false);
                }
            }
        });
    }

//    public void displayCard(List<Card> drawed) {
//        int rankOne = drawed.get(0).getRank();
//        cardOne.setText(String.valueOf(rankOne));
//
//        int rankTwo = drawed.get(1).getRank();
//        cardTwo.setText(String.valueOf(rankTwo));
//
//        int rankThree = drawed.get(2).getRank();
//        cardThree.setText(String.valueOf(rankThree));
//
//        int rankFour = drawed.get(3).getRank();
//        cardFour.setText(String.valueOf(rankFour));
//
//        showSolution.setText("");
//    }

    public void displayCards(List<Card> drawed) {
        cardOneRank.setForeground(Color.BLACK);
        cardTwoRank.setForeground(Color.BLACK);
        cardThreeRank.setForeground(Color.BLACK);
        cardFourRank.setForeground(Color.BLACK);

        displayCardRank(drawed.get(0), cardOneRank);
        displayCardSuit(drawed.get(0), cardOneSuit);

        displayCardRank(drawed.get(1), cardTwoRank);
        displayCardSuit(drawed.get(1), cardTwoSuit);

        displayCardRank(drawed.get(2), cardThreeRank);
        displayCardSuit(drawed.get(2), cardThreeSuit);

        displayCardRank(drawed.get(3), cardFourRank);
        displayCardSuit(drawed.get(3), cardFourSuit);

        showSolution.setText("");
    }

    public void displayCardRank(Card card, JLabel jLabel) {
        int rank = card.getRank();
        String suit = card.getSuit();

        switch (rank) {
            case 11:
                jLabel.setText("J");
                break;
            case 12:
                jLabel.setText("Q");
                break;
            case 13:
                jLabel.setText("K");
                break;
            default:
                jLabel.setText(String.valueOf(rank));
        }

        if ((suit == "Heart") || (suit == "Diamond")) {
            jLabel.setForeground(Color.RED);
        }
    }

    public void displayCardSuit(Card card, JLabel jLabel) {
        String suit = card.getSuit();
        ImageIcon spade = new ImageIcon("Images/spade.png");
        ImageIcon heart = new ImageIcon("Images/heart.png");
        ImageIcon diamond = new ImageIcon("Images/diamond.png");
        ImageIcon club = new ImageIcon("Images/club.png");
        switch (suit) {
            case "Spade":
                jLabel.setIcon(spade);
                break;
            case "Heart":
                jLabel.setIcon(heart);
                break;
            case "Diamond":
                jLabel.setIcon(diamond);
                break;
            case "Club":
                jLabel.setIcon(club);
                break;
        }
    }

    public void solution() {
        solution = new JButton("Check if there is a solution");
        solution.setBounds(1200,500,200,25);
        panel.add(solution);
        showSolution = new JLabel();
        showSolution.setBounds(1200,550,400,25);
        panel.add(showSolution);
        solution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(HomePage.wholeGame.getCurrentGame().getCardDeck().judgePoint24()) {
                    showSolution.setText("There is a solution");
                } else {
                    showSolution.setText("No solution");
                }
            }
        });
    }

        public void playerOne() {
        Player one = HomePage.wholeGame.getCurrentGame().getPlayerOne();
        JButton playerOne = new JButton(HomePage.wholeGame.getCurrentGame().getPlayerOne().getName());
        playerOne.setBounds(1200,200,100,25);
        panel.add(playerOne);
        playerOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                one.addFour();
                scoreOne.setText(String.valueOf(one.getNumCards()));
            }
        });
    }

        public void playerTwo() {
        Player two = HomePage.wholeGame.getCurrentGame().getPlayerTwo();
        JButton playerTwo = new JButton(HomePage.wholeGame.getCurrentGame().getPlayerTwo().getName());
        playerTwo.setBounds(1300,200,100,25);
        panel.add(playerTwo);
        playerTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                two.addFour();
                scoreTwo.setText(String.valueOf(two.getNumCards()));
            }
        });
    }

    public void tie() {
        Player playerOne = HomePage.wholeGame.getCurrentGame().getPlayerOne();
        Player playerTwo = HomePage.wholeGame.getCurrentGame().getPlayerTwo();
        JButton tie = new JButton("tie");
        tie.setBounds(1200,400,200,25);
        panel.add(tie);
        tie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerOne.addTwo();
                playerTwo.addTwo();
                scoreOne.setText(String.valueOf(playerOne.getNumCards()));
                scoreTwo.setText(String.valueOf(playerTwo.getNumCards()));
            }
        });
    }

        public void displayScore() {
        scoreOne = new JLabel("0");
        scoreOne.setBounds(1210,260,100,50);
        panel.add(scoreOne);
        scoreTwo = new JLabel("0");
        scoreTwo.setBounds(1325,260,100,50);
        panel.add(scoreTwo);
        Font font = new Font("Ariel", Font.BOLD, 50);
        scoreOne.setFont(font);
        scoreTwo.setFont(font);
    }

    //EFFECT: initiate JLabels for cards
    public void displayCardBackground() {
        Font font = new Font("Ariel", Font.BOLD, 100);

        cardOneRank = new JLabel();
        cardOneRank.setBounds(110,300,150,100);
        panel.add(cardOneRank);
        cardOneRank.setFont(font);
        cardOneSuit = new JLabel();
        cardOneSuit.setBounds(100,400,150,150);
        panel.add(cardOneSuit);

        cardTwoRank = new JLabel();
        cardTwoRank.setBounds(300,300,150,100);
        panel.add(cardTwoRank);
        cardTwoRank.setFont(font);
        cardTwoSuit = new JLabel();
        cardTwoSuit.setBounds(310,400,150,150);
        panel.add(cardTwoSuit);

        cardThreeRank = new JLabel();
        cardThreeRank.setBounds(510,300,150,100);
        panel.add(cardThreeRank);
        cardThreeRank.setFont(font);
        cardThreeSuit = new JLabel();
        cardThreeSuit.setBounds(500,400,150,150);
        panel.add(cardThreeSuit);

        cardFourRank = new JLabel();
        cardFourRank.setBounds(710,300,150,100);
        panel.add(cardFourRank);
        cardFourRank.setFont(font);
        cardFourSuit = new JLabel();
        cardFourSuit.setBounds(700,400,150,150);
        panel.add(cardFourSuit);
    }

    public void displayRound() {
        round = new JLabel("Round 0");
        round.setBounds(1200,50,150,25);
        panel.add(round);
        Font font = new Font("Ariel", Font.BOLD, 24);
        round.setFont(font);
    }

    public void quit() {
        JButton quit = new JButton("Quit");
        quit.setBounds(1200,700,100,25);
        panel.add(quit);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game currentGame = HomePage.wholeGame.getCurrentGame();
                currentGame.killGame();
                currentGame.setWholeGame(HomePage.wholeGame);
                frame.setVisible(false);
            }
        });
    }

}
