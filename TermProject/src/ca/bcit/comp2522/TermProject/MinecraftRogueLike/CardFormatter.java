package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * CardFormatter Interface helps in formatting the cards.
 * @author Vincent Fung
 * @version 2024
 */
public interface CardFormatter
{
    String PIPE = "|";
    String CARD_BORDER_LINE = "+----------------------------------+";
    String BLANK = " ";
    int OFFSET = 1;
    int PIPE_AMT = 2;

    /**
     * Helper function that formats the card correctly.
     * @param stringToFormat as the string to format
     * @return the formattedCard as a String.
     */
    default String formatCard(final String stringToFormat)
    {
        final int totalLength;
        final int lengthToCalc;
        final int padding;
        final int amtOfSpaces;
        final String output;

        totalLength = CARD_BORDER_LINE.length();
        lengthToCalc = stringToFormat.contains("%d")
                ? stringToFormat.length() - OFFSET
                : stringToFormat.length();
        padding = (totalLength - lengthToCalc - PIPE_AMT) / PIPE_AMT;
        amtOfSpaces = totalLength - lengthToCalc - padding - PIPE_AMT;

        output = PIPE + BLANK.repeat(padding) + stringToFormat +
                 BLANK.repeat(amtOfSpaces) + PIPE;

        return output;
    }
}
