package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a card associated with an event in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class EventCard extends Card {
    private final String cardDescription;
    private final List<String> requiredCards;

    /**
     * Constructs an {@code EventCard} with the specified name.
     * @param name the name of the event card
     */
    public EventCard(final String name) {
        super(name);

        requiredCards = new ArrayList<>();
        cardDescription = handleDescription(name);
        handleRequiredCards(name);
    }

    /*
     * Handles the description of the cards.
     * @param name as the name of the card to set
     * @return the int the card should have
     */
    private void handleRequiredCards(final String name)
    {
        switch(name)
        {
            case "LavaPool" -> {
                requiredCards.add("WaterBucket");
                requiredCards.add("FlintAndSteel");
            }
            case "Blaze" -> requiredCards.add("EnderPearl");
            default -> System.out.println("Error.");
        }
    }

    /*
     * Handles the attack accuracy depending on what card it is.
     * @param name as the name of the card to set
     * @return the int the card should have
     */
    private String handleDescription(final String name)
    {
        return switch(name)
        {
            case "EnderDragon" -> "Beats the game.";
            case "LavaPool" -> "Starts the Blaze Event.";
            case "Blaze" -> "Plays the EnderDragon Event.";
            default -> "Error.";
        };
    }

    /**
     * Prints the details of the event card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(super.getName() + " (Event)");
    }

    /**
     * Returns a string representation of the event card.
     * @return a string describing the event card
     */
    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder();

        sb.append("+------------------+");
        sb.append(formatCard(super.getName()));
        sb.append(formatCard("Event"));
        sb.append("+------------------+");
        sb.append(formatCard(cardDescription));
        sb.append("+------------------+");
        return sb.toString();
    }
}
