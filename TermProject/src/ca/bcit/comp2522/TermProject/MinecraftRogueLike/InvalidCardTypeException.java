package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents an exception that is thrown when an invalid card type is encountered.
 * This is a checked exception that extends {@link Exception}.
 * @author Vincent Fung
 * @version 2024
 */
public class InvalidCardTypeException extends Exception {

    /**
     * Constructs an {@code InvalidCardTypeException} with a default message.
     */
    public InvalidCardTypeException() {
        super("Invalid card type encountered.");
    }

    /**
     * Constructs an {@code InvalidCardTypeException} with a custom message.
     *
     * @param message the detail message for the exception
     */
    public InvalidCardTypeException(String message) {
        super(message);
    }
}
