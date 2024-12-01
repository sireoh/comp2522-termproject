package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

public interface CardFormatter
{
    String PIPES = "||";
    String CARD_BORDER_LINE = "+----------------------------------+";
    int OFFSET = 1;

    /**
     * Helper function that formats the card correctly.
     * @param stringToFormat as the string to format
     * @return the formattedCard as a String.
     */
    default String formatCard(final String stringToFormat) {
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
