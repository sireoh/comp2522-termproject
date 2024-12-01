package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CardFactory represents a Card Factory.
 * @author Vincent Fung
 * @version 2024
 */
public class CardFactory {
    private final static String INPUT_PATH = "resources/myGame/cardData.txt";
    private final static int VALID_ITEM = 2;
    private final static int LEFT_SIDE = 0;
    private final static int RIGHT_SIDE = 1;

    /**
     * Parses the cardData.txt file and generates a deck.
     * @return a generated deck as a List of type T.
     */
    public static List<Card> generateDeck() {
        final List<Card> generatedDeck = new ArrayList<>();
        final Path inputFile = Paths.get(INPUT_PATH);

        try (final BufferedReader reader = Files.newBufferedReader(inputFile)) {
            String currentLine;
            String cardName;
            String cardType;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\|");
                if (parts.length == VALID_ITEM) {
                    cardName = parts[LEFT_SIDE].trim();
                    cardType = parts[RIGHT_SIDE].trim();
                    Card createdCard = createCard(cardName, cardType); // Create Card (not a specific subclass)
                    generatedDeck.add(createdCard);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(generatedDeck);

        return generatedDeck; // Return List<Card>
    }

    /**
     * Helper function that creates the card for the CardFactory.
     * @param cardName as the name of the card.
     * @param cardType as the type of Card to produce.
     * @return a Card of its specific type.
     */
    private static Card createCard(final String cardName, String cardType) {
        return switch(cardType)
        {
            case "TokenCard" -> new TokenCard(cardName);
            case "WeaponCard" -> new WeaponCard(cardName);
            case "EventCard" -> new EventCard(cardName);
            case "ActivatableCard" -> new ActivatableCard(cardName);
            default -> throw new RuntimeException("Invalid card type: " + cardType);
        };
    }


}
