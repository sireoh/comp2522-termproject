package ca.bcit.comp2522.TermProject.MinecraftRogueLike;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * MyGame represents Minecraft Roguelike.
 * @author Vincent Fung
 * @version 2024
 */
public class MinecraftRogueLike
{
    private static final int OFFSET = 1;
    private List<Card> deck;
    private List<Card> hand;
    private final static Scanner scanner;
    private final static int STARTING_ROUND = 1;
    private static final int CHOOSE_SWAP = 1;
    private static final int CHOOSE_DRAW = 2;
    private static final int CHOOSE_EVENT = 3;
    private static final int TOKEN_CARD = 1;
    private static final int WEAPON_CARD = 2;
    private static final int ACTIVATABLE_CARD = 3;
    private static final int STARTING_INDEX = 0;
    private static final int MAX_CHOICE_SIZE = 3;
    private static final int MAX_SWAP_SIZE = 3;

    static {
        scanner = new Scanner(System.in);
    }

    /**
     * Initialises the deck and hand.
     * Uses Concurrency to handle the file reading.
     */
    public MinecraftRogueLike() {
        final ExecutorService executorService;
        executorService = Executors.newSingleThreadExecutor();
        Future<List<Card>> futureDeck;
        futureDeck = executorService.submit(CardFactory::generateDeck);

        try {
            deck = futureDeck.get();
            hand = GameHandler.generateHand(deck);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        BossFightEventHandler.resetBossEvent();
        play();
    }

    /**
     * Constructor for Testing Purposes
     * @param testDeck as the test deck
     * @param testHand as the test hand
     */
    public MinecraftRogueLike(List<Card> testDeck, List<Card> testHand) {
        this.deck = new ArrayList<>(testDeck);
        this.hand = new ArrayList<>(testHand);
    }

    /**
     * Getter function for the Hand list.
     * @return the hand as a List
     */
    public List<Card> getHand()
    {
        return hand;
    }

    /**
     * Getter function for the Deck List.
     * @return the deck as a List.
     */
    public List<Card> getDeck()
    {
        return deck;
    }

    /**
     * Prints the deck details.
     */
    public void printDeckDetails()
    {
        deck.forEach(Card::printDetails);
    }

    /**
     * Prints the hand details.
     */
    public void printHandDetails()
    {
        hand.forEach(card -> System.out.println(card.toString()));
    }

    /**
     * Entry point for MyGame.
     */
    public void play() {
        List<Card> hand = getHand();
        List<Card> deck = getDeck();
        int round = STARTING_ROUND;

        while (!deck.isEmpty() && !BossFightEventHandler.hasBossFightStarted()) {
            System.out.println("Round " + round);
            printHandDetails();

            System.out.println("Choose an action:");
            System.out.println(CHOOSE_SWAP + ". Swap card");
            System.out.println(CHOOSE_DRAW + ". Draw from deck");
            System.out.println(CHOOSE_EVENT + ". Play event card (if any)");

            int choice;
            choice = GameHandler.makeChoice(STARTING_INDEX, MAX_CHOICE_SIZE);

            if (choice == CHOOSE_SWAP) {
                final int secondChoice;
                System.out.println("You have chosen to swap a card.");
                System.out.println("Choose an action:");
                System.out.println(TOKEN_CARD + ". Swap a Token card");
                System.out.println(WEAPON_CARD + ". Swap a Weapon card");
                System.out.println(ACTIVATABLE_CARD + ". Swap an Activatable card");

                secondChoice = GameHandler.makeChoice(STARTING_INDEX, MAX_SWAP_SIZE);
                switch (secondChoice)
                {
                    case TOKEN_CARD -> GameHandler.swapCardOfType(hand, deck, TokenCard.class);
                    case WEAPON_CARD -> GameHandler.swapCardOfType(hand, deck, WeaponCard.class);
                    case ACTIVATABLE_CARD -> GameHandler.swapCardOfType(hand, deck, ActivatableCard.class);
                    default -> System.out.println("Invalid choice");
                }
            }
            else if (choice == CHOOSE_DRAW)
            {
                System.out.println("You have chosen to draw a card from the deck.");
                try
                {
                    GameHandler.drawCard(hand, deck);
                    System.out.println("Successfully drew a card!");
                }
                catch (final DeckEmptyException e)
                {
                    System.out.println("The deck is empty. No cards can be drawn.");
                }
            } else if (choice == CHOOSE_EVENT)
            {
                final List<Card> eventCardsInHand;
                eventCardsInHand = hand.stream()
                        .filter(card -> card instanceof EventCard)
                        .toList();

                if (eventCardsInHand.isEmpty())
                {
                    System.out.println("No event cards in hand to play.");
                }
                else
                {
                    final int eventCardChoice;
                    final Card chosenEventCard;

                    System.out.println("Choose an EventCard to play:");
                    GameHandler.generateOptionsList(eventCardsInHand);

                    eventCardChoice = GameHandler.makeChoice(STARTING_INDEX, eventCardsInHand.size());
                    chosenEventCard = eventCardsInHand.get(eventCardChoice - OFFSET);

                    GameHandler.playEventCard(chosenEventCard, hand, deck);
                }
            }
            else
            {
                System.out.println("Invalid choice, please choose again.");
            }
            round++;
        }

        BossFightEventHandler.handleBossFightLoop(hand);

        System.out.println("Final Hand:");

        printHandDetails();
    }
}
