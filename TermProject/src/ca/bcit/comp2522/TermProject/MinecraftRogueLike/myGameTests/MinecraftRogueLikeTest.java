import ca.bcit.comp2522.TermProject.MinecraftRogueLike.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinecraftRogueLikeTest {
    private MinecraftRogueLike game;
    private List<Card> testDeck;
    private List<Card> testHand;

    @BeforeEach
    void setUp() {
        System.out.println("Setting it up.");

        // Initialize a mutable test deck
        testDeck = new ArrayList<>(List.of(
                new TokenCard("TestToken1"),
                new WeaponCard("TestWeapon1"),
                new ActivatableCard("TestActivatable1"),
                new EventCard("TestEvent1")
        ));

        // Clone the deck for the hand to avoid modifying the original
        List<Card> clonedDeck = new ArrayList<>(testDeck);
        testHand = GameHandler.generateHand(clonedDeck);

        // Add a replacement card explicitly for the test
        testDeck.add(new TokenCard("ReplacementCard"));

        game = new MinecraftRogueLike(new ArrayList<>(testDeck), testHand);
    }

    @Test
    void testHandInitialization() {
        assertNotNull(testHand, "Hand should be initialized.");
        assertFalse(testHand.isEmpty(), "Hand should not be empty.");
    }

    @Test
    void testDrawCard() {
        int initialDeckSize = testDeck.size();
        int initialHandSize = testHand.size();

        GameHandler.drawCard(testHand, testDeck);

        assertEquals(initialDeckSize - 1, testDeck.size(), "Deck size should decrease by 1.");
        assertEquals(initialHandSize + 1, testHand.size(), "Hand size should increase by 1.");
    }

    @Test
    void testDrawCardEmptyDeck() {
        testDeck.clear();
        assertThrows(DeckEmptyException.class, () -> GameHandler.drawCard(testHand, testDeck),
                "Drawing a card from an empty deck should throw DeckEmptyException.");
    }

    @Test
    void testValidCardTypesInDeck() {
        testDeck.forEach(card -> assertTrue(
                card instanceof TokenCard || card instanceof WeaponCard || card instanceof ActivatableCard || card instanceof EventCard,
                "Each card should be a valid type."
        ));
    }

    @Test
    void testCardFactory() {
        List<Card> deck = CardFactory.generateDeck();
        assertNotNull(deck, "Generated deck should not be null.");
        assertFalse(deck.isEmpty(), "Generated deck should not be empty.");
    }

    @Test
    void testCardFormatting() {
        Card sampleCard = new TokenCard("TestCard");
        assertNotNull(sampleCard.toString(), "Card string representation should not be null.");
        assertEquals("TestCard", sampleCard.getName(), "Card name should match the input.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing it down.");
        testDeck.clear();
        testHand.clear();
    }
}
