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

    public static void playEventCard(final Card eventCard, final List<Card> hand, final List<Card> deck) {
}
