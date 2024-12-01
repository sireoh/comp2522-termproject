package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles game-related operations such as managing the deck and player hands.
 */
public class GameHandler {
    private final static Scanner scanner;
    private final static Card card;
    private final static int STARTING_INDEX = 0;
    private final static int OFFSET = 1;

    static {
        card = new EventCard("");
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

        drawnCard = deck.removeFirst();
        hand.add(drawnCard);
    }

    /**
     * Swaps an item in the hand with a similar item in the deck.
     * @param hand as the List representing the current hand.
     * @param deck as the List representing remaining cards in the deck.
     * @param type as the type of card.
     */
    public static void swapCardOfType(final List<Card> hand, final List<Card> deck, final Class<? extends Card> type) {
        final Card chosenCardFromHand;
        final Card chosenCardFromDeck;
        final List<Card> filteredDeck;
        final List<Card> filteredHand;

        if (deck.isEmpty()) {
            throw new DeckEmptyException();
        }

        filteredDeck = deck.stream()
                .filter(type::isInstance)
                .toList();

        filteredHand = hand.stream()
                .filter(type::isInstance)
                .toList();

        chosenCardFromHand = promptCardToSwap(filteredHand);
        chosenCardFromDeck = promptCardToSwap(filteredDeck);

        if (chosenCardFromHand == null ||
                chosenCardFromDeck == null)
        {
            System.out.println("There are no items of that type for you to choose from.");
            return;
        }

        // Removes the previous card from the hand and deck
        removeCardByName(hand, chosenCardFromHand.getName());
        removeCardByName(deck, chosenCardFromDeck.getName());

        hand.add(chosenCardFromDeck);
    }

    /*
     * Helper function that helps prompt the card to swap.
     * @param hand as the hand to update
     * @param deck as the deck to update
     */
    private static Card promptCardToSwap(final List<Card> cardsToChooseFrom)
    {
        final int choice;
        final Card chosenCard;

        if (cardsToChooseFrom.isEmpty())
        {
            return null;
        }

        System.out.println("Which card would you like to swap?");
        generateOptionsList(cardsToChooseFrom);

        choice = makeChoice(STARTING_INDEX, cardsToChooseFrom.size());
        chosenCard = cardsToChooseFrom.get(choice - OFFSET);

        System.out.println("You chose: " + choice);
        System.out.println(chosenCard);

        return chosenCard;
    }

    /*
     * Generates a menu that shows what's available in the list.
     * @param cardsToChooseFrom as the list of cards to choose from.
     */
    private static void generateOptionsList(final List<Card> cardsToChooseFrom) {
        Card cardOption;

        // Iterate through the list of cards and print the options in the desired format
        for (int i = 0; i < cardsToChooseFrom.size(); i++) {
            cardOption = cardsToChooseFrom.get(i);
            System.out.println("+----------------------------------+");
            System.out.printf(card.formatCard("To choose, enter: %d") + "\n", (i + 1));
            System.out.println(cardOption);
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

    /*
     * Helper function that loops and helps with choices
     * @param lowerBound as the lower bound option.
     * @param upperBound as the upper bound option.
     * @return the choice, once valid.
     */
    public static int makeChoice(final int lowerBound, final int upperBound)
    {
        while(true)
        {
            try
            {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if(choice > lowerBound && choice <= upperBound)
                {
                    return choice;
                } else
                {
                    System.out.println("Invalid choice, please choose again.");
                }
            } catch(NumberFormatException e)
            {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
