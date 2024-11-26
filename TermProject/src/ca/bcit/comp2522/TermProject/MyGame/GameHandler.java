package ca.bcit.comp2522.TermProject.MyGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles game-related operations such as managing the deck and player hands.
 */
public class GameHandler {

    /**
     * Generates a hand of unique card types from the deck.
     * Each card type is represented only once in the hand, and the corresponding cards
     * are removed from the deck.
     * @param deck the deck of cards to draw from
     * @return a list of unique cards representing the generated hand
     */
    public static List<Card> generateHand(final List<Card> deck) {
        final List<Card> generatedHand;
        final Map<Class<? extends Card>, Card> handMap;

        handMap = deck.stream()
                .collect(Collectors.toMap(
                        Card::getClass,
                        card -> card,
                        (existing, replacement) -> existing));


        generatedHand = new ArrayList<>(handMap.values());
        deck.removeAll(handMap.values());
        return generatedHand;
    }

    /**
     * Draws a card from the deck and adds it to the player's hand.
     * Throws a {@link DeckEmptyException} if the deck is empty.
     * @param hand the player's current hand
     * @param deck the deck of cards to draw from
     */
    public static void drawCard(final List<Card> hand, final List<Card> deck) {
        final Card drawnCard;

        if (deck.isEmpty()) {
            throw new DeckEmptyException();
        }

        drawnCard = ((LinkedList<Card>) deck).removeFirst();
        hand.add(drawnCard);
    }
}
