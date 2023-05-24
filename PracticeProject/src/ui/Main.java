package ui;

import Model.Exception.InvalidInputException;
import Model.Exception.NoCardException;
import Model.WholeGame;
import Persistence.JsonWriter;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws InvalidInputException, NoCardException, FileNotFoundException {
        //new Console();
        new HomePage();
    }

}