package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Abstract Card class, parent of Cards.
 * @author Vincent Fung
 * @version 2024
 */
public abstract class Card {
    private final String name;
    private final static int OFFSET = 1;
    private final static String PIPES = "||";
    private final static String CARD_BORDER_LINE = "+----------------------------------+";

    /**
     * Card constructor for Card object.
     * @param name as a String
     */
    public Card(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    abstract void printDetails();

    /**
     * Helper function that formats the card correctly.
     * @param stringToFormat as the string to format
     * @return the formattedCard as a String.
     */
    public String formatCard(final String stringToFormat) {
        final int totalLength;
        final int lengthToCalc;
        final int padding;
        final String output;

        totalLength = CARD_BORDER_LINE.length();
        lengthToCalc = stringToFormat.contains("%d")
                ? stringToFormat.length() - OFFSET
                : stringToFormat.length();

        // Calculate the padding required on each side to center the card name
        padding = (totalLength - lengthToCalc - PIPES.length()) / PIPES.length();
        output = "|" + " ".repeat(padding) +
                stringToFormat + " ".repeat(totalLength - lengthToCalc - padding - PIPES.length()) + "|";

        return output;
    }


}