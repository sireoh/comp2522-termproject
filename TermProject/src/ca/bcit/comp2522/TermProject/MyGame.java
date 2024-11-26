package ca.bcit.comp2522.TermProject;

import java.util.*;

public class MyGame {
    private final Map<Character, String> cardList; // Mapping card keys to their names
    private final Deque<Character> deck;          // Shuffled deck
    private final static String indexToChar = "A234567890JQK";

    public MyGame() {
        // Initialize cardList with card mappings
        cardList = new HashMap<>();
        cardList.put('A', "Diamond Pick");
        cardList.put('2', "Totem of Undying");
        cardList.put('3', "Ender Pearls");
        cardList.put('4', "Gold Ore");
        cardList.put('5', "Bow");
        cardList.put('6', "Iron Ore");
        cardList.put('7', "Stone Axe");
        cardList.put('8', "Water Bucket");
        cardList.put('9', "Flint & Steel");
        cardList.put('0', "Enchanted Netherite Sword");
        cardList.put('J', "Lava Pool");
        cardList.put('Q', "Blaze");
        cardList.put('K', "Ender Dragon");

        // Shuffle the deck
        List<Character> shuffledKeys = new ArrayList<>(cardList.keySet());
        Collections.shuffle(shuffledKeys);

        // Use a Deque for the deck to easily draw cards
        deck = new ArrayDeque<>(shuffledKeys);
    }

    // Method to deal a hand of 5 cards
    public String[] getHand() {
        String[] hand = new String[5];
        for (int i = 0; i < hand.length; i++) {
            if (deck.isEmpty()) {
                throw new IllegalStateException("Deck is empty! Cannot deal more cards.");
            }
            char cardKey = deck.poll(); // Remove and retrieve the top card
            hand[i] = cardList.get(cardKey);
        }
        return hand;
    }

    // Method to draw a single card
    public String drawCard() {
        if (deck.isEmpty()) {
            throw new IllegalStateException("Deck is empty! Cannot draw a card.");
        }
        char cardKey = deck.poll(); // Remove and retrieve the top card
        return cardList.get(cardKey);
    }

    public static void main(String[] args) {
        MyGame game = new MyGame();

        // Get and print a hand of 5 cards
        System.out.println("Dealing a hand of 5 cards:");
        String[] hand = game.getHand();
        System.out.println(Arrays.toString(hand));

        // Draw and print a single card
        System.out.println("\nDrawing a single card:");
        System.out.println(game.drawCard());

        // Draw more cards until the deck is empty
        System.out.println("\nDrawing remaining cards:");
        while (true) {
            try {
                System.out.println(game.drawCard());
            } catch (IllegalStateException e) {
                System.out.println("Deck is empty.");
                break;
            }
        }
    }
}
