package ca.bcit.comp2522.TermProject.MyGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardFactory {
    public static List<Card> generateDeck()
    {
        List<Card> generatedDeck = new ArrayList<Card>();
        Path inputFile = Paths.get("resources/myGame/cardData.txt");

        try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into card name and type
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String cardName = parts[0].trim();  // Get the card type
                    String cardType = parts[1].trim();  // Get the card type
                    Card card = CardFactory.createCard(cardName, cardType);  // Create the card
                    generatedDeck.add(card);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(generatedDeck);

        return generatedDeck;
    }

    private static Card createCard(final String cardName, String cardType) {
        return switch (cardType) {
            case "TokenCard" -> new TokenCard(cardName);
            case "WeaponCard" -> new WeaponCard(cardName);
            case "EventCard" -> new EventCard(cardName);
            case "ActivatableCard" -> new ActivatableCard(cardName);
            default -> {
                try{ throw new InvalidCardTypeException(); }
                catch(InvalidCardTypeException e) { throw new RuntimeException(e); }
            }
        };
    }
}
