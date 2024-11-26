package ca.bcit.comp2522.TermProject.MyGame;

public class InvalidCardTypeException extends Exception
{
    // Default constructor
    public InvalidCardTypeException() {
        super("The deck is empty.");
    }

    // Constructor that accepts a custom message
    public InvalidCardTypeException(String message) {
        super(message);
    }
}
