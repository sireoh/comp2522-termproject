package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card that acts as a token in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class TokenCard extends Card {
    /**
     * Constructs a {@code TokenCard} with the specified name.
     * @param name the name of the token card
     */
    public TokenCard(final String name) {
        super(name);
    }

    /**
     * Prints the details of the token card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(super.getName() + " (Token)");
    }

    /**
     * Returns a string representation of the token card.
     * @return a string describing the token card
     */
    @Override
    public String toString() {
        return super.getName() + " (Token)";
    }
}
