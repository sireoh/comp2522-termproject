package ca.bcit.comp2522.TermProject.MyGame;

import java.util.*;
import java.util.stream.Collectors;

public class GameHandler
{
    public static List<Card> generateHand(List<Card> deck)
    {
        final List<Card> generatedHand;

        Map<Class<? extends Card>, Card> hand = deck.stream()
                .collect(Collectors.toMap(
                        Card::getClass,
                        card -> card,
                        (existing, replacement) -> existing));
        generatedHand = new ArrayList<>(hand.values());

        System.out.println("Heres ur hand");
        generatedHand.forEach(Card::printDetails);

        System.out.println("\nCurrent state of deck.");
        deck.removeAll(hand.values());
        deck.forEach(Card::printDetails);

        return generatedHand;
    }

    public static Card drawCard(List<Card> deck)
    {
        if (deck.isEmpty())
        {
            throw new DeckEmptyException();
        }

        return null;
    }
}
