package ui;

import Model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class ViewProfile implements ActionListener {
    protected JPanel panel;
    protected JFrame frame;
    protected JComboBox playerList;
    protected  JLabel select;
    protected JLabel win;
    protected JLabel lose;
    protected JLabel tie;
    protected JLabel total;

    public ViewProfile() {
        this.panel = new JPanel();
        this.frame = new JFrame();
        frame.setSize(500,500);
        panel.setLayout(null);
        displayLabels();
        createPlayerList();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void createPlayerList() {
        Set<String> players = HomePage.wholeGame.getPlayers().keySet();
        String[] options = players.toArray(new String[0]);
        playerList = new JComboBox<>(options);
        playerList.setBounds(200,75,100,25);
        playerList.addActionListener(this);
        panel.add(playerList);
        }

    public void displayLabels() {
        select = new JLabel("Select Player");
        select.setBounds(100,75,100,25);
        panel.add(select);
        win = new JLabel("# Win: ");
        win.setBounds(100,150,100,25);
        panel.add(win);
        lose = new JLabel("# Lose: ");
        lose.setBounds(100,200,100,25);
        panel.add(lose);
        tie = new JLabel("# Tie: ");
        tie.setBounds(100,250,100,25);
        panel.add(tie);
        total = new JLabel("# Total: ");
        total.setBounds(100,300,100,25);
        panel.add(total);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedOption = (String) playerList.getSelectedItem();
        Player player = HomePage.wholeGame.getPlayers().get(selectedOption);
        win.setText("# Win: " + player.getNumWon());
        lose.setText("# Lose: " + player.getNumLost());
        tie.setText("# Tie: " + player.getNumTie());
        total.setText("# Total: " + player.getTotalGames());
    }
}
