package ca.bcit.comp2522.TermProject.MinecraftRogueLike;
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
    private List<Card> deck;
    private List<Card> hand;
    private final static Scanner scanner;
    private final static int STARTING_ROUND = 1;
    private static final int CHOOSE_SWAP = 1;
    private static final int CHOOSE_DRAW = 2;
    private static final int TOKEN_CARD = 1;
    private static final int WEAPON_CARD = 2;
    private static final int ACTIVATABLE_CARD = 3;
    private static final int STARTING_INDEX = 0;
    private static final int MAX_CHOICE_SIZE = 2;
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

        play();
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

        while (!deck.isEmpty()) {
            System.out.println("Round " + round);
            printHandDetails();

            System.out.println("Choose an action:");
            System.out.println(CHOOSE_SWAP + ". Swap card");
            System.out.println(CHOOSE_DRAW + ". Draw from deck");

            int choice = GameHandler.makeChoice(STARTING_INDEX, MAX_CHOICE_SIZE);

            if (choice == CHOOSE_SWAP) {
                System.out.println("You have chose to swap a card.");
                System.out.println("Choose an action:");
                System.out.println(TOKEN_CARD + ". Swap a Token card");
                System.out.println(WEAPON_CARD + ". Swap a Weapon card");
                System.out.println(ACTIVATABLE_CARD + ". Swap an Activatable card");

                int secondChoice = GameHandler.makeChoice(STARTING_INDEX, MAX_SWAP_SIZE);
                switch (secondChoice) {
                    case TOKEN_CARD -> GameHandler.swapCardOfType(hand, deck, TokenCard.class);
                    case WEAPON_CARD -> GameHandler.swapCardOfType(hand, deck, WeaponCard.class);
                    case ACTIVATABLE_CARD -> GameHandler.swapCardOfType(hand, deck, ActivatableCard.class);
                    default -> System.out.println("Invalid choice");
                }
            } else if (choice == CHOOSE_DRAW) {
                System.out.println("You have chose to draw a card from the deck.");
                GameHandler.drawCard(hand, deck);
            } else {
                System.out.println("Invalid choice, please choose again.");
            }

            round++;
        }

        System.out.println("Game Over! Final Hand:");
        printHandDetails();
    }
}
