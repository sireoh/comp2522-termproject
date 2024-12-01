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

    public String formatCard(final String stringToFormat) {
        // Updated total length of the card (including pipes), adjusted for longer boundaries
        int totalLength = 36; // Default width including pipes
        int lengthToCalc = stringToFormat.contains("%d")
                ? stringToFormat.length() - 1
                : stringToFormat.length();

        // Calculate the padding required on each side to center the card name
        int padding = (totalLength - lengthToCalc - 2) / 2; // Subtract 2 for pipes

        return "|" + " ".repeat(padding) + stringToFormat + " ".repeat(totalLength - lengthToCalc - padding - 2) + "|";
    }


}