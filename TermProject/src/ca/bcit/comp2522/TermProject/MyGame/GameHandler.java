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

        deck.removeAll(hand.values());

        return generatedHand;
    }

    public static void drawCard(final List<Card> hand, final List<Card> deck)
    {
        final Card drawnCard;

        if (deck.isEmpty())
        {
            throw new DeckEmptyException();
        }

        drawnCard = deck.removeFirst();
        hand.add(drawnCard);
    }
}
