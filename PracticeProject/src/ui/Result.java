package ui;

import Model.Game;
import Model.Player;
import Test.persistence.JSONTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Result {
    protected JPanel panel;
    protected JFrame frame;
    protected JLabel result;
    protected Game currentGame;
    protected Player pOne;
    protected Player pTwo;

    public Result() {
        this.panel = new JPanel(new GridBagLayout());
        this.frame = new JFrame();
        frame.setSize(500,300);
        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
        currentGame = HomePage.wholeGame.getCurrentGame();
        pOne = currentGame.getPlayerOne();
        pTwo = currentGame.getPlayerTwo();
        displayResultBackground();
        displayResult();
        displayPlayerScore();
    }

    public void displayResultBackground() {
        result = new JLabel();
        result.setBounds(50,50,150,25);
        panel.add(result);
        Font font = new Font("Ariel", Font.BOLD,22);
        result.setFont(font);
    }

    public void displayResult() {
        int one = pOne.getNumCards();
        int two = pTwo.getNumCards();
        if (one > two) {
            pOne.win();
            pTwo.lose();
            result.setText(pOne.getName() + " Wins!");
        } else if (two > one) {
            pOne.lose();
            pTwo.win();
            result.setText(pTwo.getName() + " Wins!");
        } else {
            pOne.tie();
            pTwo.tie();
            result.setText("Tie!");
        }
    }

    public void displayPlayerScore() {
        String nameOne = pOne.getName();
        String nameTwo = pTwo.getName();
        String[] colNames = {"", nameOne, nameTwo};

        int winOne = pOne.getNumWon();
        int loseOne = pOne.getNumLost();
        int tieOne = pOne.getNumTie();
        int totalOne = pOne.getTotalGames();

        int winTwo = pTwo.getNumWon();
        int loseTwo = pTwo.getNumLost();
        int tieTwo = pTwo.getNumTie();
        int totalTwo = pTwo.getTotalGames();

        Object[][] data = {
                {"# Win", winOne, winTwo},
                {"# Lose", loseOne, loseTwo},
                {"# Tie", tieOne, tieTwo},
                {"# Total", totalOne, totalTwo}
        };

        DefaultTableModel model = new DefaultTableModel(data, colNames);
        JTable table = new JTable(model);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 50;
        constraints.gridy = 100;
        panel.add(table, constraints);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,100, 400,100);
        panel.add(scrollPane);
    }
    
}
