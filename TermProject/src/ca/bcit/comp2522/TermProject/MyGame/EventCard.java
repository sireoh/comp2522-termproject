package ca.bcit.comp2522.TermProject.MyGame;

/**
 * Represents a card associated with an event in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class EventCard implements Card {
    private final String name;

    /**
     * Constructs an {@code EventCard} with the specified name.
     * @param name the name of the event card
     */
    public EventCard(final String name) {
        this.name = name;
    }

    /**
     * Prints the details of the event card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(name + " (Event)");
    }

    /**
     * Returns a string representation of the event card.
     * @return a string describing the event card
     */
    @Override
    public String toString() {
        return name + " (Event)";
    }
}
