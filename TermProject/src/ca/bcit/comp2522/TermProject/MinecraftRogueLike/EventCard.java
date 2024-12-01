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
        cardDescription = handleDescription(name, this.getClass());
        handleRequiredCards(name, requiredCards);
    }

    /**
     * Prints the details of the event card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(toString());
    }

    /**
     * Returns a string representation of the event card.
     * @return a string describing the event card
     */
    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder();

        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(super.getName())).append("\n");
        sb.append(formatCard("Event")).append("\n");
        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(cardDescription)).append("\n");

        if (!requiredCards.isEmpty()) {
            sb.append(formatCard("Required:")).append("\n");
            sb.append(formatRequiredCards(requiredCards));
        }

        sb.append(CARD_BORDER_LINE);
        return sb.toString();
    }

}
