package ca.bcit.comp2522.TermProject.MyGame;

/**
 * Represents a card that can be activated during the game.
 * @author Vincent Fung
 * @version 2024
 */
public class ActivatableCard implements Card {
    private final String name;

    /**
     * Constructs an {@code ActivatableCard} with the specified name.
     * @param name the name of the activatable card
     */
    public ActivatableCard(final String name) {
        this.name = name;
    }

    /**
     * Prints the details of the activatable card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(name + " (Activatable)");
    }

    /**
     * Returns a string representation of the activatable card.
     * @return a string describing the activatable card
     */
    @Override
    public String toString() {
        return name + " (Activatable)";
    }
}
