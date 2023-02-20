package Models.Exceptions;

public class NoCardException extends Exception{

    public NoCardException() {
        super("No card left");
    }
}
