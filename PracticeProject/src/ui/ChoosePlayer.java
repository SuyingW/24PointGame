package ui;

import Model.Exception.DuplicatePlayerException;
import Model.Exception.NoPlayerException;
import Model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class ChoosePlayer implements ActionListener{
    protected JPanel panel;
    protected JFrame frame;
    protected JComboBox playerOne;
    protected JComboBox playerTwo;
    protected JLabel messageOne;
    protected JLabel messageTwo;
    protected JButton start;

    public ChoosePlayer() {
        this.panel = new JPanel();
        this.frame = new JFrame();
        frame.setSize(500,500);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
        displayText();
        selectPlayerOne();
        selectPlayerTwo();
        displayButton();
    }

    public void displayText() {
        JLabel selectPlayerOne = new JLabel("Select Player One");
        selectPlayerOne.setBounds(100,100,150,25);
        panel.add(selectPlayerOne);
        JLabel selectPlayerTwo = new JLabel("Select Player Two");
        selectPlayerTwo.setBounds(100, 150, 150, 25);
        panel.add(selectPlayerTwo);
        messageOne = new JLabel("Player One: ");
        messageOne.setBounds(100,200,300,25);
        panel.add(messageOne);
        messageTwo = new JLabel("Player Two: ");
        messageTwo.setBounds(100,250,300,25);
        panel.add(messageTwo);
    }

    public void selectPlayerOne() {
        Set<String> players = HomePage.wholeGame.getPlayers().keySet();
        String[] options = players.toArray(new String[0]);
        playerOne = new JComboBox<>(options);
        playerOne.setBounds(250,100,100,25);
        panel.add(playerOne);
        playerOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) playerOne.getSelectedItem();
                try {
                    HomePage.wholeGame.getCurrentGame().selectPlayerOne(selectedOption);
                    messageOne.setText("Player One: " + selectedOption);
                } catch (NoPlayerException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void selectPlayerTwo() {
        Set<String> players = HomePage.wholeGame.getPlayers().keySet();
        String[] options = players.toArray(new String[0]);
        playerTwo = new JComboBox<>(options);
        playerTwo.setBounds(250,150,100,25);
        panel.add(playerTwo);
        playerTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) playerTwo.getSelectedItem();
                try {
                    HomePage.wholeGame.getCurrentGame().selectPlayerTwo(selectedOption);
                    messageTwo.setText("Player Two: " + selectedOption);
                } catch (NoPlayerException ex) {
                    throw new RuntimeException(ex);
                } catch (DuplicatePlayerException ex) {
                    messageTwo.setText("PlayerTwo has to be Different from PlayerOne");
                }
            }
        });
    }

    public void displayButton() {
        start = new JButton("Start Game");
        start.setBounds(100,300,100,25);
        start.addActionListener(this);
        panel.add(start);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Draw();
        frame.setVisible(false);
    }
}
