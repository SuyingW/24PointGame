package ui;

import Model.Event;
import Model.Game;
import Model.WholeGame;
import Persistence.JsonReader;
import Persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomePage extends JFrame {
    protected JPanel panel;
    protected JFrame frame;
    protected JButton start;
    protected JButton add;
    protected JButton view;
    //protected JButton quit;
    protected JButton rule;

    protected static WholeGame wholeGame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/WholeGame.json";

    //EFFECT: runs the 24 point game application
    public HomePage() throws FileNotFoundException {
        this.wholeGame = new WholeGame();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadFromFile();
        constructFrame();
        this.frame.setVisible(true);
        frame.getGraphics();
        displayTitle();
        displayButtons();
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    //EFFECT: construct a frame and a panel for background
    public void constructFrame() {
        this.panel = new JPanel();
        this.frame = new JFrame();
        frame.setSize(1500,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(HomePage.this,
                        "Do you want to save the file to JSON?",
                        "Save to JSON",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    saveToFile();
                    dispose();
                }
                System.out.println("Below is the log:");
                for (Event next : model.EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
            }
        });
    }

    //EFFECT: display title
    public void displayTitle() {
        JLabel title = new JLabel("24 Point Game");
        Font font = new Font("Arial", Font.BOLD, 40);
        title.setBounds(600,50,1000,50);
        title.setFont(font);
        //panel.add(title);
    }

    public void displayButtons() {
        start();
        add();
        view();
        rule();
        //quit();
    }

    //EFFECT: display start, add, view, load, quit
    public void start() {
        start = new JButton("Start a New Game");
        start.setBounds(650, 150, 150, 50);
        panel.add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage.wholeGame.getCurrentGame().setWholeGame(HomePage.wholeGame);
                new ChoosePlayer();
            }
        });
    }
     public void add() {
         add = new JButton("Create a New Player");
         add.setBounds(650, 250, 150, 50);
         panel.add(add);
         add.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new CreatePlayer();
             }
         });
     }

     public void view() {
         view = new JButton("View Players' Profiles");
         view.setBounds(650, 350, 150, 50);
         panel.add(view);
         view.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new ViewProfile();
             }
         });
     }

     public void rule() {
         rule = new JButton("Rule");
         rule.setBounds(650, 450, 150, 50);
         panel.add(rule);
         rule.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new Rule();
             }
         });
     }

//     public void quit() {
//        quit = new JButton("Save");
//        quit.setBounds(650,550,150,50);
//        panel.add(quit);
//        quit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    jsonWriter.open();
//                    jsonWriter.write(wholeGame);
//                    jsonWriter.close();
//                    JOptionPane.showMessageDialog(null, "Successfully saved your game!",
//                            "Status", JOptionPane.INFORMATION_MESSAGE);
//                } catch (FileNotFoundException ex) {
//                    JOptionPane.showMessageDialog(null,
//                            "Oops! Something went wrong when trying to save your game!",
//                            "Status", JOptionPane.INFORMATION_MESSAGE);
//                }
//
//            }
//        });
//    }

    public void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(wholeGame);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Successfully saved your game!",
                    "Status", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Oops! Something went wrong when trying to save your game!",
                    "Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadFromFile() {
        try {
            wholeGame = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Successfully loaded your previously saved game!",
                    "Status", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Oops! Failed to load your previously saved game!",
                    "Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
