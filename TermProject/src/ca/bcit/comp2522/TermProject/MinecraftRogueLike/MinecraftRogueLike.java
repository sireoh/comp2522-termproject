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

    static {
        scanner = new Scanner(System.in);
    }

    /**
     * Initialises the deck and hand.
     */
    public MinecraftRogueLike() {
        final ExecutorService executorService;
        executorService = Executors.newSingleThreadExecutor();
        Future<List<Card>> futureDeck;
        futureDeck = executorService.submit(CardFactory::getDeck);

        try {
            deck = futureDeck.get();
            hand = GameHandler.generateHand(deck);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
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
     * @param args unused.
     */
    public static void main(String[] args) {
        MinecraftRogueLike game = new MinecraftRogueLike();
        List<Card> hand = game.getHand();
        List<Card> deck = game.getDeck();
        int round = STARTING_ROUND;

        while (!deck.isEmpty()) {
            System.out.println("Round " + round);
            game.printHandDetails();

            System.out.println("Choose an action:");
            System.out.println("1. Swap card");
            System.out.println("2. Draw from deck");

            int choice = GameHandler.makeChoice(0, 2);

            if (choice == 1) {
                System.out.println("You have chose to swap a card.");
                System.out.println("Choose an action:");
                System.out.println("1. Swap a Token card");
                System.out.println("2. Swap a Weapon card");
                System.out.println("3. Swap a Activatable card");
                int secondChoice = GameHandler.makeChoice(0, 3);
                switch (secondChoice) {
                    case 1 -> GameHandler.swapCardOfType(hand, deck, TokenCard.class);
                    case 2 -> GameHandler.swapCardOfType(hand, deck, WeaponCard.class);
                    case 3 -> GameHandler.swapCardOfType(hand, deck, ActivatableCard.class);
                    default -> System.out.println("Invalid choice");
                }
            } else if (choice == 2) {
                System.out.println("You have chose to draw a card from the deck.");
                GameHandler.drawCard(hand, deck);
            } else {
                System.out.println("Invalid choice, please choose again.");
            }

            round++;
        }

        System.out.println("Game Over! Final Hand:");
        game.printHandDetails();
    }
}
