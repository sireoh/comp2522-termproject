package ca.bcit.comp2522.TermProject.MyGame;
import java.util.List;

public class MyGame {
    private final List<Card> deck;
    private final List<Card> hand;

    public MyGame()
    {
        deck = CardFactory.generateDeck();
        hand = GameHandler.generateHand(deck);
    }

    public List<Card> getHand()
    {
        return hand;
    }

    public List<Card> getDeck()
    {
        return deck;
    }
    public void printDeckDetails()
    {
        deck.forEach(Card::printDetails);
    }

    public void printHandDetails()
    {
        hand.forEach(card -> System.out.println("- " + card.toString()));
    }

    public static void main(String[] args) {
        MyGame game = new MyGame();
        System.out.println("Welcome to MINECRAFT ROGUELIKE");
        System.out.println("Here is your current hand");
        game.printHandDetails();
    }
}
