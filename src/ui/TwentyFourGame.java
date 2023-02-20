package ui;

import Models.Card;
import Models.Exceptions.DuplicatePlayerException;
import Models.Exceptions.InvalidInputException;
import Models.Exceptions.NoCardException;
import Models.Exceptions.NoPlayerException;
import Models.Game;
import Models.Player;
import Models.WholeGame;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Reference: TellerApp
public class TwentyFourGame {
    public WholeGame wholeGame;
    public Scanner input;

    public TwentyFourGame() throws
            NoCardException,
            NoPlayerException,
            InvalidInputException,
            DuplicatePlayerException {
        runGame();
    }

    private void runGame() throws
            NoCardException,
            NoPlayerException,
            InvalidInputException,
            DuplicatePlayerException {

        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);
        init();

        while(keepGoing) {
            displayMenuOne();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Game ends");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws
            NoCardException,
            NoPlayerException,
            InvalidInputException,
            DuplicatePlayerException {
        if(command.equals("s")) {
            doStartGame();
        } else if (command.equals("v")) {
            doViewProfiles();
        } else if (command.equals("a")) {
            doAddNewPlayer();
        } else if (command.equals("d")) {
            doDrawCards();
        } else if (command.equals("one")) {
            doPlayerOne();
        } else if (command.equals("two")) {
            doPlayerTwo();
        } else if (command.equals("t")) {
            doTie();
        } else if (command.equals("show")) {
            showWhetherSolution();
            selectWhoisFaster();
        } else {
            throw new InvalidInputException();
        }
    }

    //EFFECTS: display if there is a solution
    private void showWhetherSolution() {
        if(this.wholeGame.getCurrentGame().getCardDeck().judgePoint24()) {
            System.out.println("There is a solution");
        } else {
            System.out.println("No solution");
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes a whole game
    private void init() {
        input = new Scanner(System.in);
        wholeGame = new WholeGame();
        input.useDelimiter("\n");
    }


    // EFFECTS: displays menu of options to user
    private void displayMenuOne() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> start a new game");
        System.out.println("\tv -> view player profiles");
        System.out.println("\ta -> add new players");
        System.out.println("\tq - quit");
    }

    // EFFECTS: set players
    private void doStartGame() throws
            NoCardException,
            NoPlayerException,
            InvalidInputException,
            DuplicatePlayerException {
        selectPlayerOne();
        selectPlayerTwo();
        displayDrawFour();
    }

    //todo: handle the e
    // EFFECTS: display prompt to draw four
    private void displayDrawFour() throws
            NoCardException,
            InvalidInputException,
            NoPlayerException,
            DuplicatePlayerException {
        System.out.println("\td -> draw cards");
        String input = this.input.nextLine();
        if(input.equals("d")) {
            doDrawCards();
            selectWhoisFaster();
        } else {
            System.out.println("Invalid Input");
            displayDrawFour();
        }
    }

    private void selectWhoisFaster() throws
            NoCardException,
            NoPlayerException,
            InvalidInputException,
            DuplicatePlayerException {
        Player playerOne = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playerTwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        displayMenuTwo();
        String input = this.input.nextLine();
        try {
            processCommand(input);
        } catch (InvalidInputException e) {
            System.out.println("Invalid Input");
            selectWhoisFaster();
        }
        System.out.println(playerOne.getName() + ": " + playerOne.getNumCards());
        System.out.println(playerTwo.getName() + ": " + playerTwo.getNumCards());
        whetherToContinue();
    }

    // EFFECTS: let user decide to continue or to end game
    private void whetherToContinue() throws InvalidInputException, NoPlayerException, DuplicatePlayerException {
        System.out.println("\tc -> continue");
        String toContinue = this.input.nextLine();
        if (toContinue.equals("c")) {
            try {
                displayDrawFour();
            } catch (NoCardException e) {
                finishGame();
            }
        } else {
            whetherToContinue();
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenuTwo() {
        System.out.println("\tshow -> show whether there is a solution");
        System.out.println("\tone -> Player One");
        System.out.println("\ttwo -> Player Two");
        System.out.println("\tt -> tie");
        System.out.println("\te -> end game");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenuThree() {
        System.out.println("\te -> enter another player name");
        System.out.println("\ta -> add new player");
        System.out.println("\tc -> check existing players");
    }
    // EFFECTS: update card allocation when player one is faster in this round
    private void doPlayerOne() {
        List<Card> drawed = this.wholeGame.getCurrentGame().getCardDeck().getDrawed();
        this.wholeGame.getCurrentGame().getPlayerOne().addFour(drawed);
    }

    // EFFECTS: update card allocation when player two is faster in this round
    private void doPlayerTwo() {
        List<Card> drawed = this.wholeGame.getCurrentGame().getCardDeck().getDrawed();
        this.wholeGame.getCurrentGame().getPlayerTwo().addFour(drawed);
    }

    // EFFECTS: update card allocation when the two players are equally fast or there is no solution
    private void doTie() {
        List<Card> drawed = this.wholeGame.getCurrentGame().getCardDeck().getDrawed();
        List<Card> oneAdd = new ArrayList<>();
        oneAdd.add(drawed.get(0));
        oneAdd.add(drawed.get(1));
        List<Card> twoAdd = new ArrayList<>();
        oneAdd.add(drawed.get(2));
        oneAdd.add(drawed.get(3));
        this.wholeGame.getCurrentGame().getPlayerOne().addTwo(oneAdd);
        this.wholeGame.getCurrentGame().getPlayerTwo().addTwo(twoAdd);
    }


    private void finishGame() {
        Player playerone = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playertwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        if (playerone.getNumCards() > playertwo.getNumCards()) {
            finalPlayerOneWins();
        } else if (playerone.getNumCards() < playertwo.getNumCards()){
            finalPlayerTwoWins();
        } else {
            finalTie();
        }
        killCurrentGame();
    }

    // EFFECTS: update and display players' game history when it's a tie
    private void finalTie() {
        Player playerone = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playertwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        playerone.tie();
        playertwo.tie();
        System.out.println("\nTie");
        System.out.println("\nPlayer One: " + playerone.getName());
        System.out.println("\tWon: " + playerone.getNumWon());
        System.out.println("\tLost: " + playerone.getNumLost());
        System.out.println("\tTied: " + playerone.getNumTie());
        System.out.println("\tTotal: " + playerone.getTotalGames());
        System.out.println("\nPlayer Two: " + playertwo.getName());
        System.out.println("\tWon: " + playertwo.getNumWon());
        System.out.println("\tLost: " + playertwo.getNumLost());
        System.out.println("\tTied: " + playertwo.getNumTie());
        System.out.println("\tTotal: " + playertwo.getTotalGames());
        System.out.println("\tGame Ends");
    }


    // EFFECTS: update and display players' game history when player one wins
    private void finalPlayerOneWins() {
        Player playerone = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playertwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        playerone.win();
        playertwo.lose();
        System.out.println(playerone.getName() + " wins!");
        System.out.println("\nPlayer One: " + playerone.getName());
        System.out.println("\tWon: " + playerone.getNumWon());
        System.out.println("\tLost: " + playerone.getNumLost());
        System.out.println("\tTied: " + playerone.getNumTie());
        System.out.println("\tTotal: " + playerone.getTotalGames());
        System.out.println("\nPlayer Two: " + playertwo.getName());
        System.out.println("\tWon: " + playertwo.getNumWon());
        System.out.println("\tLost: " + playertwo.getNumLost());
        System.out.println("\tTied: " + playertwo.getNumTie());
        System.out.println("\tTotal: " + playertwo.getTotalGames());
        System.out.println("\tGame Ends");
    }


    // EFFECTS: update and display players' game history when player two wins
    private void finalPlayerTwoWins() {
        Player playerone = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playertwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        playerone.win();
        playertwo.lose();
        System.out.println(playertwo.getName() + " wins!");
        System.out.println("\nPlayer One: " + playerone.getName());
        System.out.println("\tWon: " + playerone.getNumWon());
        System.out.println("\tLost: " + playerone.getNumLost());
        System.out.println("\tTied: " + playerone.getNumTie());
        System.out.println("\tTotal: " + playerone.getTotalGames());
        System.out.println("\nPlayer Two: " + playertwo.getName());
        System.out.println("\tWon: " + playertwo.getNumWon());
        System.out.println("\tLost: " + playertwo.getNumLost());
        System.out.println("\tTied: " + playertwo.getNumTie());
        System.out.println("\tTotal: " + playertwo.getTotalGames());
        System.out.println("\tGame Ends");
    }


    // EFFECTS: draw four cards
    private void doDrawCards() throws NoCardException {
        this.wholeGame.getCurrentGame().getCardDeck().drawFour();
        List<Card> drawed = this.wholeGame.getCurrentGame().getCardDeck().getDrawed();
        this.wholeGame.getCurrentGame().incrementCurrentRound();
        System.out.println("Round: " + this.wholeGame.getCurrentGame().getCurrentRound() + "/13");
        for (Card card : drawed) {
            System.out.println(card.getRank());
        }

    }

    // MODIFIES: this
    // EFFECTS: add a new player to the whole game
    private void doAddNewPlayer() {
        System.out.println("Enter player's name: ");
        String name = this.input.nextLine();
        Player player = new Player(name);

        try {
            this.wholeGame.addPlayer(player);
        } catch (DuplicatePlayerException e) {
            System.out.println("Duplicate Player, Change Name Please!");
            doAddNewPlayer();
        }
    }


    // EFFECTS: display menu for selecting player one
    private void selectPlayerOne() throws NoPlayerException {
        System.out.println("\nPlayer One:");
        String name = input.nextLine();
        try {
            this.wholeGame.getCurrentGame().setPlayerOne(name);
        } catch (NoPlayerException e) {
            System.out.println("This player doesn't exist");
            displayMenuThree();
            String selection = input.nextLine();
            switch(selection) {
                case "a":
                    doAddNewPlayer();
                    selectPlayerOne();
                    break;
                case "e" :
                    selectPlayerOne();
                    break;
                case "c":
                    checkPlayerNames();
                    selectPlayerOne();
            }

        }
    }


    // EFFECTS: display menu for selecting player two
    private void selectPlayerTwo() throws NoPlayerException, DuplicatePlayerException {
        System.out.println("\nPlayer Two:");
        String name = input.nextLine();
        try {
            this.wholeGame.getCurrentGame().setPlayerTwo(name);
        } catch (NoPlayerException e) {
            System.out.println("This player doesn't exist");
            displayMenuThree();
            String selection = input.nextLine();
            switch (selection) {
                case "a":
                    doAddNewPlayer();
                    selectPlayerTwo();
                    break;
                case "e":
                    selectPlayerTwo();
                    break;
                case "c":
                    checkPlayerNames();
                    selectPlayerTwo();
            }
        } catch (DuplicatePlayerException e) {
            System.out.println("Can't have the same player");
            selectPlayerTwo();
        }
    }


        // EFFECTS: display a player's game history
    private void doViewProfiles() {
        for (Player p : this.wholeGame.getPlayers()) {
            System.out.println(p.getName());
        }
        System.out.println("Enter player's name: ");
        String name = this.input.nextLine();
        for (Player player : this.wholeGame.getPlayers()) {
            if (player.getName().equals(name)) {
                System.out.println("\tWon: " + player.getNumWon());
                System.out.println("\tLost: " + player.getNumLost());
                System.out.println("\tTied: " + player.getNumTie());
                System.out.println("\tTotal: " + player.getTotalGames());
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: when players end game at the middle of a game, create a new game with no players
    private void killCurrentGame() {
        Player playerone = this.wholeGame.getCurrentGame().getPlayerOne();
        Player playertwo = this.wholeGame.getCurrentGame().getPlayerTwo();
        playerone.setNumCardsZero();
        playertwo.setNumCardsZero();
        Game newGame = new Game();
        newGame.addtoWholeGame(this.wholeGame);
        this.wholeGame.setCurrentGame(newGame);
    }

    // EFFECTS: display all the players' names
    private void checkPlayerNames() {
        for (Player p : this.wholeGame.getPlayers()) {
            System.out.println(p.getName());
        }
    }
}
