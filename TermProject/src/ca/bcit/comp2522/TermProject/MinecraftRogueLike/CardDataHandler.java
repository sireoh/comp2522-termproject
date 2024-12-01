package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.ArrayList;
import java.util.List;

public abstract class CardDataHandler implements CardFormatter
{
    final List<String> tempObtainedCards;
    private final static String EMPTY_STRING = "";

    {
        tempObtainedCards = new ArrayList<String>();
    }
    /*
     * Handles the description of the cards.
     * @param name as the name of the card to set
     * @return the int the card should have
     */
    protected List<String> handleObtainedCards(final String name)
    {
        tempObtainedCards.clear();

        switch(name)
        {
            case "DiamondPick" -> {
                tempObtainedCards.add("WaterBucket");
                tempObtainedCards.add("FlintAndSteel");
            }
            case "IronOre" -> tempObtainedCards.add("WaterBucket");
            case "GoldIngots" -> tempObtainedCards.add("EnderPearls");
        }

        return tempObtainedCards;
    }

    /*
     * Helper function that gives the requirements as a String
     * @return the requirements as a string
     */
    protected String formatObtainedCards()
    {
        final StringBuilder output;
        output = new StringBuilder();

        for (final String requiredCard : tempObtainedCards)
        {
            output.append(formatCard("- " + requiredCard)).append("\n");
        }

        return output.toString();
    }

    /*
     * Handles the description of the cards.
     * @param name as the name of the card to set
     * @return the int the card should have
     */
    protected void handleRequiredCards(final String name, final List<String> requiredCards)
    {
        switch(name)
        {
            case "LavaPool" -> {
                requiredCards.add("WaterBucket");
                requiredCards.add("FlintAndSteel");
            }
            case "Blaze" -> requiredCards.add("EnderPearls");
        }
    }

    /*
     * Helper function that gives the requirements as a String
     * @return the requirements as a string
     */
    protected String formatRequiredCards(final List<String> requiredCards)
    {
        StringBuilder output;
        output = new StringBuilder();

        for (final String requiredCard : requiredCards)
        {
            output.append(formatCard("- " + requiredCard)).append("\n");
        }

        return output.toString();
    }

    /**
     * Handles the card description depending on what card it is.
     * @param name the name of the card to set
     * @param type the type of the card
     * @return the description of the card
     */
    protected String handleDescription(final String name, final Class<? extends Card> type) {
        if (ActivatableCard.class.isAssignableFrom(type)) {
            return switch (name) {
                case "TotemOfUndying" -> "Prevents one death";
                default -> "Discard one";
            };
        }
        if (WeaponCard.class.isAssignableFrom(type)) {
            return switch(name)
            {
                case "EnchantedNetheriteSword" -> "OHKO's the dragon";
                case "StoneSword" -> "Generic RPG item";
                case "Bow" -> "64 Arrows";
                default -> EMPTY_STRING;
            };
        } else if (EventCard.class.isAssignableFrom(type)) {
            return switch (name) {
                case "EnderDragon" -> "Beats the game.";
                case "LavaPool" -> "Starts the Blaze Event.";
                case "Blaze" -> "Plays the EnderDragon Event.";
                default -> "Error.";
            };
        } else {
            return "Unknown card type.";
        }
    }
}
