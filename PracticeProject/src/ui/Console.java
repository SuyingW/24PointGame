package ui;

import Model.*;
import Model.Exception.DuplicatePlayerException;
import Model.Exception.InvalidInputException;
import Model.Exception.NoCardException;
import Model.Exception.NoPlayerException;
import Persistence.JsonReader;
import Persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Console {
    private static final String JSON_STORE = "./data/wholegame.json";
    public WholeGame wholeGame;
    public Scanner input;
    public JsonWriter jsonWriter;
    public JsonReader jsonReader;

    public Console() throws InvalidInputException, NoCardException, FileNotFoundException {
        this.wholeGame = new WholeGame();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGame();
    }

    private void runGame() throws InvalidInputException, NoCardException, FileNotFoundException {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);
        init();

        while (keepGoing) {
            displayHomePage();
            command = input.nextLine();
            command = command.toLowerCase();
            if (command.equals("quit")) {
                saveWholeGame();
                keepGoing = false;
            } else {
                processCommand(command);
                }
            }
        System.out.println("Game ends");
    }

    private void init() {
        input = new Scanner(System.in);
        wholeGame = new WholeGame();
        input.useDelimiter("\n");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws InvalidInputException, NoCardException, FileNotFoundException {
        if (command.equals("start")) {
            startGame();
        } else if (command.equals("view")) {
            displaySelectPlayer();
            viewProfiles();
        } else if (command.equals("add")) {
            addNewPlayer();
        } else if (command.equals("quit")) {
            saveWholeGame();
        } else if (command.equals("load")) {
            loadWholeGame();
        } else {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: set players
    public void startGame() throws NoCardException {
        selectPlayerOne();
        selectPlayerTwo();
        while(this.wholeGame.getCurrentGame().getCardDeck().getSize() > 0) {
            drawFour();
        }
        showResult();
    }

    //EFFECT: select player one
    public void selectPlayerOne() {
        displaySelectPlayer();
        System.out.println("\nEnter Player One:");
        String name = input.nextLine();
        try {
            this.wholeGame.getCurrentGame().selectPlayerOne(name);
        } catch (NoPlayerException e) {
            System.out.println("This player doesn't exist");
            displaySelectionsForPlayerNotExist();
            String selection = input.nextLine();
            switch(selection) {
                case "new":
                    addNewPlayer();
                    selectPlayerOne();
                    break;
                case "enter" :
                    selectPlayerOne();
                    break;
            }
        }
    }

    //EFFECT: select player two
    public void selectPlayerTwo() {
        System.out.println("\nPlayer Two:");
        String name = input.nextLine();
        try {
            this.wholeGame.getCurrentGame().selectPlayerTwo(name);
        } catch (NoPlayerException e) {
            System.out.println("This player doesn't exist");
            displaySelectionsForPlayerNotExist();
            String selection = input.nextLine();
            switch (selection) {
                case "new":
                    addNewPlayer();
                    selectPlayerTwo();
                    break;
                case "enter":
                    selectPlayerTwo();
                    break;
            }
        } catch (DuplicatePlayerException e) {
            System.out.println("Can't have the same player");
            selectPlayerTwo();
        }
    }

    //EFFECT: add a new player to whole game
    public void addNewPlayer() {
        System.out.println("Enter player's name: ");
        String name = this.input.nextLine();

        try {
            this.wholeGame.addPlayer(name);
        } catch (DuplicatePlayerException e) {
            System.out.println("Duplicated Player, Change Name Please!");
            addNewPlayer();
        }
    }

    //EFFECT: display a prompt to draw four cards
    public void drawFour() throws NoCardException {
        System.out.println("\tdraw -> draw 4 cards");
        String input = this.input.nextLine();
        if (input.equals("draw")) {
            doDrawCards();
            selectWhoisFaster();
        } else {
            System.out.println("Invalid Input");
            drawFour();
        }
    }

    // EFFECT: draw four cards
    private void doDrawCards() throws NoCardException {
        this.wholeGame.getCurrentGame().getCardDeck().drawFour();
        List<Card> drawed = this.wholeGame.getCurrentGame().getCardDeck().getDrawed();
        this.wholeGame.getCurrentGame().incrementCurrentRound();
        System.out.println("Round: " + this.wholeGame.getCurrentGame().getCurrentRound() + "/13");
        for (Card card : drawed) {
            System.out.println(card.getRank());
        }
    }

    //EFFECT: select the faster player
    private void selectWhoisFaster() {
        Player playerOne = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playerTwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        displayChooseFaster();
        String input = this.input.nextLine();
        switch(input) {
            case "one":
                playerOneFaster();
                System.out.println(playerOne.getName() + ": " + playerOne.getNumCards());
                System.out.println(playerTwo.getName() + ": " + playerTwo.getNumCards());
                break;
            case "two":
                playerTwoFaster();
                System.out.println(playerOne.getName() + ": " + playerOne.getNumCards());
                System.out.println(playerTwo.getName() + ": " + playerTwo.getNumCards());
                break;
            case "tie":
                tie();
                System.out.println(playerOne.getName() + ": " + playerOne.getNumCards());
                System.out.println(playerTwo.getName() + ": " + playerTwo.getNumCards());
                break;
            case "show":
                showWhetherSolution();
                selectWhoisFaster();
                break;
            default:
                System.out.println("Invalid Input");
                selectWhoisFaster();
                break;
        }
    }

    //EFFECTS: display menu of options for user
    private void displayHomePage() {
        System.out.println("\nSelect from:");
        System.out.println("\tload -> load game from file");
        System.out.println("\tstart -> start a new game");
        System.out.println("\tview -> view player profiles");
        System.out.println("\tadd -> add new players");
        System.out.println("\tquit - quit");
    }

    private void displaySelectPlayer() {
        System.out.println("\nSelect player:");
        HashMap<String, Player> players = this.wholeGame.getPlayers();
        for (Map.Entry<String,Player> entry : players.entrySet()) {
            String name = entry.getKey();
            System.out.println(name);
        }
    }
    private void displaySelectionsForPlayerNotExist() {
        System.out.println("\tenter -> enter another name");
        System.out.println("\tnew -> add new player");
    }

    private void displayChooseFaster() {
        System.out.println("\nSelect who is faster:");
        System.out.println("\tone -> Player One");
        System.out.println("\ttwo -> Player Two");
        System.out.println("\ttie -> tie");
        System.out.println("\tshow -> show whether there is a solution");
    }

    //EFFECTS: display if there is a solution
    private void showWhetherSolution() {
        if(this.wholeGame.getCurrentGame().getCardDeck().judgePoint24()) {
            System.out.println("There is a solution");
        } else {
            System.out.println("No solution");
        }
    }

    //EFFECT: add points for player one
    private void playerOneFaster() {
        this.wholeGame.getCurrentGame().getPlayerOne().addFour();
    }

    //EFFECT: add points for player two
    private void playerTwoFaster() {
        this.wholeGame.getCurrentGame().getPlayerTwo().addFour();
    }

    //EFFECT: add two points for each player
    private void tie() {
        this.wholeGame.getCurrentGame().getPlayerOne().addTwo();
        this.wholeGame.getCurrentGame().getPlayerTwo().addTwo();
    }

    //EFFECT：view players' profiles
    private void viewProfiles() {
        String input = this.input.nextLine();
        Player player = this.wholeGame.getPlayers().get(input);
        System.out.println("Name: " + player.getName());
        System.out.println("# Won: " + player.getNumWon());
        System.out.println("# Lost: " + player.getNumLost());
        System.out.println("# Tie: " + player.getNumTie());
        System.out.println("# Total: " + player.getTotalGames());
    }

    //EFFECT: end current game, set all fields to null or 0
    private void killGame() {
         Game currentGame = this.wholeGame.getCurrentGame();
         Player playerOne = currentGame.getPlayerOne();
         playerOne.setNumCards(0);
         Player playerTwo = currentGame.getPlayerTwo();
         playerTwo.setNumCards(0);
         currentGame.setPlayerOne(null);
         currentGame.setPlayerTwo(null);
         currentGame.setCardDeck(new CardDeck());
         currentGame.setCurrentRound(0);
         currentGame.setWholeGame(this.wholeGame);
    }

    //EFFECT: show which player wins and their current statistics
    private void showResult() {
        Game currentGame = this.wholeGame.getCurrentGame();
        Player playerOne = currentGame.getPlayerOne();
        Player playerTwo = currentGame.getPlayerTwo();
        int one = currentGame.getPlayerOne().getNumCards();
        int two = currentGame.getPlayerTwo().getNumCards();
        if (one > two) {
            playerOne.win();
            playerTwo.lose();
            System.out.println(currentGame.getPlayerOne().getName() + " Wins!");
        } else if (two > one) {
            playerOne.lose();
            playerTwo.win();
            System.out.println(currentGame.getPlayerTwo().getName() + " Wins!");
        } else {
            playerOne.tie();
            playerTwo.tie();
            System.out.println("Tie!");
        }
        killGame();
    }

    //EFFECT: save the whole game to json file
    public void saveWholeGame() throws FileNotFoundException {
        try {
            jsonWriter.open();
            jsonWriter.write(this.wholeGame);
            jsonWriter.close();
            System.out.println("Successfully saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write the game to " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECT: loads whole game from json file
    public void loadWholeGame() {
        try {
            wholeGame = jsonReader.read();
            System.out.println("Loaded successfully from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }


}
