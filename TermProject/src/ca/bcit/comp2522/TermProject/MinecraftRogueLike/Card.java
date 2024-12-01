package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.List;

/**
 * Abstract Card class, parent of Cards.
 * @author Vincent Fung
 * @version 2024
 */
public abstract class Card extends CardDataHandler implements CardFormatter
{
    protected final static String CARD_BORDER_LINE = "+----------------------------------+\n";
    private final String name;
    private String cardDescription;
    private List<String> obtainedCards;

    /**
     * Card constructor for Card object.
     * @param name as a String
     */
    public Card(final String name)
    {
        this.name = name;
    }

    /**
     * Getter function, gets the name.
     * @return the name as a String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter function, gets the cardDescription.
     * @return the name as a String
     */
    public String getCardDescription()
    {
        return cardDescription;
    }

    /**
     * Getter function, gets the cardDescription.
     *
     * @return the name as a String
     */
    public List<String> getObtainedCards()
    {
        return obtainedCards;
    }

    /**
     * Setter function for the card description
     * @param cardDescription as a String
     */
    public void setCardDescription(final String cardDescription)
    {
        this.cardDescription = cardDescription;
    }

    /**
     * Setter function for the obtained cards
     * @param obtainedCards as a String
     */
    public void setObtainedCards(final List<String> obtainedCards)
    {
        this.obtainedCards = obtainedCards;
    }

    /**
     * Abstract function that prints the details.
     */
    abstract void printDetails();
}