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

    public void printDeckDetails()
    {
        deck.forEach(Card::printDetails);
    }

    public void printHandDetails()
    {
        hand.forEach(Card::printDetails);
    }

    public static void main(String[] args) {
        final MyGame game = new MyGame();
    }
}
