package Model.Exception;

public class NoPlayerException extends Exception{

    public NoPlayerException() {
        super("The player does not exist");
    }
}
