package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a card that can be activated during the game.
 * @author Vincent Fung
 * @version 2024
 */
public class ActivatableCard extends Card {
    private final String cardDescription;
    private final List<String> obtainedCards;
    private final static String CARD_BORDER_LINE = "+----------------------------------+\n";

    /**
     * Constructs an {@code ActivatableCard} with the specified name.
     * @param name the name of the activatable card
     */
    public ActivatableCard(final String name) {
        super(name);

        obtainedCards = new ArrayList<>();
        cardDescription = handleDescription(name);
        handleObtainedCards(name);
    }

    /*
     * Handles the description of the cards.
     * @param name as the name of the card to set
     * @return the int the card should have
     */
    private void handleObtainedCards(final String name)
    {
        switch(name)
        {
            case "DiamondPick" -> {
                obtainedCards.add("WaterBucket");
                obtainedCards.add("FlintAndSteel");
            }
            case "IronOre" -> obtainedCards.add("WaterBucket");
            case "GoldIngots" -> obtainedCards.add("EnderPearls");
        }
    }
    /*
     * Helper function that gives the requirements as a String
     * @return the requirements as a string
     */
    private String getObtainedCards()
    {
        StringBuilder output;
        output = new StringBuilder();

        for (final String requiredCard : obtainedCards)
        {
            output.append(formatCard("- " + requiredCard)).append("\n");
        }

        return output.toString();
    }

    /**
     * Prints the details of the activatable card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(toString());
    }

    /*
     * Handles the attack accuracy depending on what card it is.
     * @param name as the name of the card to set
     * @return the String the card should have
     */
    private String handleDescription(final String name)
    {
        return switch(name)
        {
            case "TotemOfUndying" -> "Prevents one death";
            default -> "Discard one";
        };
    }

    /**
     * Returns a string representation of the activatable card.
     * @return a string describing the activatable card
     */
    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder();

        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(super.getName())).append("\n");
        sb.append(formatCard("Activatable")).append("\n");
        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(cardDescription)).append("\n");

        if (!obtainedCards.isEmpty()) {
            sb.append(formatCard("to obtain")).append("\n");
            sb.append(formatCard("either:")).append("\n");
            sb.append(getObtainedCards());
        }

        sb.append(CARD_BORDER_LINE);
        return sb.toString();
    }
}
