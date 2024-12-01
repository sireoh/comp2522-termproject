package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Card interface, represents a card.
 * @author Vincent Fung
 * @version 2024
 */
public abstract class Card {
    private final String name;

    Card(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    abstract void printDetails();

    public String formatCard(final String stringToFormat)
    {
        // Total length of the card (including pipes), e.g., 18 characters in total
        int totalLength = 18;

        // Calculate the padding required on each side to center the card name
        int padding = (totalLength - stringToFormat.length() - 2) / 2;

        return "|" + " ".repeat(padding) + stringToFormat + " ".repeat(totalLength - stringToFormat.length() - padding - 2) + "|";
    };
}