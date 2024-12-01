package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card that acts as a token in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class TokenCard extends Card {
    final StringBuilder sb;
    final static int EMPTY_STRING_BUILDER = 0;
    private final static String CARD_BORDER_LINE = "+----------------------------------+\n";

    /**
     * Constructs a {@code TokenCard} with the specified name.
     * @param name the name of the token card
     */
    public TokenCard(final String name) {
        super(name);

        sb = new StringBuilder();
    }

    /**
     * Prints the details of the token card to the console.
     */
    @Override
    public void printDetails() {
        sb.setLength(EMPTY_STRING_BUILDER);

        sb.append("+------------------+");
        sb.append(formatCard(super.getName()));
        sb.append(formatCard("Token"));
        sb.append("+------------------+");
        sb.append("+------------------+");
        System.out.println(sb.toString());
    }

    /**
     * Returns a string representation of the token card.
     * @return a string describing the token card
     */
    @Override
    public String toString() {
        sb.setLength(EMPTY_STRING_BUILDER);

        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(super.getName())).append("\n");
        sb.append(formatCard("Token")).append("\n");
        sb.append(CARD_BORDER_LINE);
        return sb.toString();
    }
}
