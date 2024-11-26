package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card that can be activated during the game.
 * @author Vincent Fung
 * @version 2024
 */
public class ActivatableCard extends Card {

    /**
     * Constructs an {@code ActivatableCard} with the specified name.
     * @param name the name of the activatable card
     */
    public ActivatableCard(final String name) {
        super(name);
    }

    /**
     * Prints the details of the activatable card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(super.getName() + " (Activatable)");
    }

    /**
     * Returns a string representation of the activatable card.
     * @return a string describing the activatable card
     */
    @Override
    public String toString() {
        return super.getName() + " (Activatable)";
    }
}
