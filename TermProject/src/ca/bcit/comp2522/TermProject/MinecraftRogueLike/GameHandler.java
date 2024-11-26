package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles game-related operations such as managing the deck and player hands.
 */
public class GameHandler {
    private final static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

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

    /**
     * Swaps an item in the hand with a similar item in the deck.
     * @param hand as the List representing the current hand.
     * @param deck as the List representing remaining cards in the deck.
     * @param type as the type of card.
     */
    public static void swapCardOfType(final List<Card> hand, final List<Card> deck, final Class<? extends Card> type) {
        Card drawnCard;
        final List<Card> filteredCards;
        final String cardToSwap;

        drawnCard = null;

        if (deck.isEmpty()) {
            throw new DeckEmptyException();
        }

        filteredCards = deck.stream()
                .filter(type::isInstance)
                .toList();

        filteredCards.forEach(card -> System.out.println("- " + card.toString()));
        cardToSwap = scanner.nextLine();

        for (final Card card : filteredCards)
        {
            if (card.getName().equals(cardToSwap))
            {
                System.out.println("Swapping " + cardToSwap + " with " + card.toString());
                drawnCard = card;
                removeCardByName(deck, drawnCard.getName());
                hand.add(drawnCard);
            }
        }

        if (drawnCard != null)
        {
            drawnCard.printDetails();
        }
    }

    /*
     * Helper function removes the card from the deck.
     * @param deck as the deck to remove from.
     */
    private static void removeCardByName(final List<Card> deck, final String cardName)
    {
        deck.stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .ifPresent(deck::remove);
    }
}
