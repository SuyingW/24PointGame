package ui;

import javax.swing.*;
import java.awt.*;

public class Rule {

    protected JPanel panel;
    protected JFrame frame;
    protected JLabel rule;

    public Rule() {
        this.panel = new JPanel();
        this.frame = new JFrame();
        frame.setSize(500, 400);
        frame.add(panel);
        panel.setLayout(null);
        displayRule();
        frame.setVisible(true);
    }

    public void displayRule() {
        rule = new JLabel("<html>Make 24 with all 4 numbers on the card using + - x /<br/>Must use all 4 numbers<br/>Each number can only be use once<br/>J = 11, Q = 12, K = 13, A = 1<br/>The faster player gets 4 point<br/>Each player gets 2 points if tied<br/>There will be 13 rounds of game<br/>Player with higher score at the end wins</html>");
        Font font = new Font("Arial", Font.PLAIN, 18);
        rule.setBounds(50,20,500,300);
        rule.setFont(font);
        panel.add(rule);
    }
}
