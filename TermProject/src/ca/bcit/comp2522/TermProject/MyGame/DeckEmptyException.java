package ca.bcit.comp2522.TermProject.MyGame;

public class DeckEmptyException extends RuntimeException {
    // Default constructor
    public DeckEmptyException() {
        super("The deck is empty.");
    }

    // Constructor that accepts a custom message
    public DeckEmptyException(String message) {
        super(message);
    }
}