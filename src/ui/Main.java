package ui;

import Models.Exceptions.DuplicatePlayerException;
import Models.Exceptions.InvalidInputException;
import Models.Exceptions.NoCardException;
import Models.Exceptions.NoPlayerException;

public class Main {
    public static void main(String[] args) throws NoCardException, NoPlayerException, InvalidInputException, DuplicatePlayerException {
        new TwentyFourGame();
    }
}
