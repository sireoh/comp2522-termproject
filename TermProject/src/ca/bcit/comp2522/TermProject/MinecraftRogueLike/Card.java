package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Abstract Card class, parent of Cards.
 * @author Vincent Fung
 * @version 2024
 */
public abstract class Card implements CardFormatter
{
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
}