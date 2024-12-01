import ca.bcit.comp2522.TermProject.MinecraftRogueLike.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinecraftRogueLikeTest {

    private MinecraftRogueLike game;
    private List<Card> initialDeck;
    private List<Card> initialHand;

    @BeforeEach
    void setUp() {
        game = new MinecraftRogueLike();
        initialDeck = game.getDeck();
        initialHand = game.getHand();
        System.out.println("Setting up the test environment.");
    }

    @Test
    void testDeckGeneration() {
        assertNotNull(initialDeck, "Deck should be generated and not null.");
        assertFalse(initialDeck.isEmpty(), "Deck should not be empty.");
    }

    @Test
    void testDrawCard() {
        int initialDeckSize = initialDeck.size();
        int initialHandSize = initialHand.size();

        GameHandler.drawCard(initialHand, initialDeck);

        assertEquals(initialDeckSize - 1, initialDeck.size(), "Deck size should decrease by 1 after drawing a card.");
        assertEquals(initialHandSize + 1, initialHand.size(), "Hand size should increase by 1 after drawing a card.");
    }

    @Test
    void testSwapCardOfType() {
        Card initialCardInHand = initialHand.get(0);
        GameHandler.swapCardOfType(initialHand, initialDeck, initialCardInHand.getClass());

        assertTrue(
                initialHand.stream().noneMatch(card -> card.getName().equals(initialCardInHand.getName())),
                "Hand should no longer contain the swapped card."
        );
    }

    @Test
    void testCardDetails() {
        initialHand.forEach(card -> {
            assertNotNull(card.toString(), "Card's string representation should not be null.");
            assertNotNull(card.getName(), "Card's name should not be null.");
        });
    }

    @Test
    void testEmptyDeckException() {
        initialDeck.clear();
        assertThrows(DeckEmptyException.class, () -> GameHandler.drawCard(initialHand, initialDeck),
                "Drawing a card from an empty deck should throw DeckEmptyException.");
    }

    @Test
    void testValidCardTypes() {
        for (Card card : initialDeck) {
            assertTrue(
                    card instanceof TokenCard || card instanceof WeaponCard || card instanceof ActivatableCard || card instanceof EventCard,
                    "All cards in the deck should be valid card types."
            );
        }
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing down the test environment.");
    }
}
