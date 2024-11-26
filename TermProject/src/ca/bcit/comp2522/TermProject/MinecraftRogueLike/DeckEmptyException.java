package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents an exception that is thrown when an operation is attempted on an empty deck.
 * This is an unchecked exception that extends {@link RuntimeException}.
 * @author Vincent Fung
 * @version 2024
 */
public class DeckEmptyException extends RuntimeException {

    /**
     * Constructs a {@code DeckEmptyException} with a default message.
     */
    public DeckEmptyException() {
        super("The deck is empty.");
    }

    /**
     * Constructs a {@code DeckEmptyException} with a custom message.
     * @param message the detail message for the exception
     */
    public DeckEmptyException(String message) {
        super(message);
    }
}
