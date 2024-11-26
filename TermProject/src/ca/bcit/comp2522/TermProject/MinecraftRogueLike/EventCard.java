package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card associated with an event in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class EventCard extends Card {
    /**
     * Constructs an {@code EventCard} with the specified name.
     * @param name the name of the event card
     */
    public EventCard(final String name) {
        super(name);
    }

    /**
     * Prints the details of the event card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(super.getName() + " (Event)");
    }

    /**
     * Returns a string representation of the event card.
     * @return a string describing the event card
     */
    @Override
    public String toString() {
        return super.getName() + " (Event)";
    }
}
