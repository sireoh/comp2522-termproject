package ca.bcit.comp2522.TermProject.MinecraftRogueLike;
import java.util.List;

/**
 * MyGame represents Minecraft Roguelike.
 * @author Vincent Fung
 * @version 2024
 */
public class MinecraftRogueLike
{
    private final List<Card> deck;
    private final List<Card> hand;

    /**
     * Initialises the deck and hand.
     */
    public MinecraftRogueLike()
    {
        deck = CardFactory.generateDeck();
        hand = GameHandler.generateHand(deck);
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
        hand.forEach(card -> System.out.println("- " + card.toString()));
    }

    /**
     * Entry point for MyGame.
     * @param args unused.
     */
    public static void main(final String[] args) {
        MinecraftRogueLike game = new MinecraftRogueLike();
        System.out.println("Welcome to MINECRAFT ROGUELIKE");
        System.out.println("Here is your current hand");
        game.printHandDetails();

        GameHandler.swapCardOfType(game.getHand(), game.getDeck(), WeaponCard.class);
    }
}
