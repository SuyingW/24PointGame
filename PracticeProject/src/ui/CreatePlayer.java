package ui;

import Model.Exception.DuplicatePlayerException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePlayer implements ActionListener {
    protected JFrame frame;
    protected JPanel panel;
    protected JButton add;
    protected JTextArea input;
    protected JLabel message;

    public CreatePlayer() {
        frame = new JFrame();
        frame.setSize(400,300);
        panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        frame.setVisible(true);
        displayText();
        displayTextArea();
        displayButton();
    }

    public void displayText() {
        JLabel name = new JLabel("Name:");
        name.setBounds(75,100,50,50);
        panel.add(name);
        message = new JLabel();
        message.setBounds(75,150,250,50);
        panel.add(message);
    }

    public void displayButton() {
        add = new JButton("Add");
        add.setBounds(275,100,50,50);
        add.addActionListener(this);
        panel.add(add);
    }

    public void displayTextArea() {
        input = new JTextArea();
        input.setBounds(145,100,100,50);
        panel.add(input);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = input.getText();
        try {
            HomePage.wholeGame.addPlayer(name);
            input.setText("");
            message.setText("Successfully Added " + name + " to Game!");
        } catch (DuplicatePlayerException ex) {
            message.setText("Duplicate Player! Try another name");
        }
    }
}
