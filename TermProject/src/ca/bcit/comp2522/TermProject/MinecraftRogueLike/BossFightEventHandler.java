package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

import java.util.List;

public class BossFightEventHandler {
    private static boolean hasBossFightStarted;
    private static boolean phaseOneHasCompleted;
    private static boolean phaseTwoHasCompleted;
    private final static int BOSS_FIGHT_STARTING_ROUND = 1;

    private final static int CHOICE_YES = 1;
    private final static int CHOICE_NO = 2;
    private static int round;

    static {
        hasBossFightStarted = false;
        phaseOneHasCompleted = false;
        phaseTwoHasCompleted = false;
        round = BOSS_FIGHT_STARTING_ROUND;
    }

    public static boolean hasBossFightStarted() {
        return hasBossFightStarted;
    }

    public static void beginBossFight() {
        hasBossFightStarted = true;
        System.out.println("Boss fight has begun!!!");
    }

    /**
     * Helper function handles the boss fight loop.
     */
    public static void handleBossFightLoop(final List<Card> hand) {
        handlePhaseOneCrystal(hand);
        if (phaseOneHasCompleted) {
            handlePhaseTwoDragon(hand);
        }
    }

    /**
     * Handles Phase Two: Attacking the Dragon
     */
    private static void handlePhaseTwoDragon(final List<Card> hand) {
        final boolean hasBow = GameHandler.checkForCardInHand(hand, "Bow");

        if (hasBow) {
            handleEndCrystalEventWithBow();
        } else {
            handleEndCrystalEventNoBow(hand);
        }

        round++;
    }

    /**
     * Handles Phase One: Destroying of End Crystals
     */
    private static void handlePhaseOneCrystal(final List<Card> hand) {
        final boolean hasSword = GameHandler.checkForCardInHand(hand, "EnchantedNetheriteSword");
        final boolean hasBow = GameHandler.checkForCardInHand(hand, "Bow");

        if (hasSword && hasBow) {
            System.out.println("You have both an EnchantedNetheriteSword and a Bow.");
            System.out.println("End The Game using the Sword or Remove End Crystals using the Bow?");
            System.out.println("1. Use Sword (OHKO)");
            System.out.println("2. Use Bow to Remove Crystals");

            final int choice = GameHandler.makeChoice(1, 2);
            switch (choice) {
                case 1 -> handleOHKOEvent(); // OHKO with the Sword
                case 2 -> {
                    System.out.println("All End Crystals have been removed.");
                    phaseOneHasCompleted = true;
                }
                default -> System.out.println("Invalid choice.");
            }
        } else if (hasSword) {
            // If only the sword is present
            System.out.println("You have an EnchantedNetheriteSword.");
            System.out.println("Do you want to use the Sword to end the game?");
            final int useSwordChoice = GameHandler.makeChoiceYesNo();
            switch (useSwordChoice) {
                case CHOICE_YES -> handleOHKOEvent(); // End the game with the sword
                case CHOICE_NO -> {
                    System.out.println("You decided to keep fighting.");
                    handleEndCrystalEventWithBow();
                }
                default -> System.out.println("Invalid choice.");
            }
        } else if (hasBow) {
            // If only the bow is present
            System.out.println("You have a Bow.");
            System.out.println("Remove all End Crystals using the Bow?");
            final int useBowChoice = GameHandler.makeChoiceYesNo();
            switch (useBowChoice) {
                case CHOICE_YES -> {
                    System.out.println("All End Crystals have been removed.");
                    phaseOneHasCompleted = true;
                }
                case CHOICE_NO -> System.out.println("You chose not to use the Bow.");
                default -> System.out.println("Invalid choice.");
            }
        } else {
            // If neither sword nor bow is present, handle accordingly
            System.out.println("You don't have any of the required items to proceed.");
        }
    }

    /**
     * Handles the OHKO Event
     */
    private static void handleOHKOEvent() {
        System.out.println("You have chosen to OHKO the ender dragon.");

        final int ohkoTheDragonChoice = GameHandler.makeChoiceYesNo();
        switch (ohkoTheDragonChoice) {
            case CHOICE_YES -> {
                System.out.println("You have won the game.");
                phaseOneHasCompleted = true;
                phaseTwoHasCompleted = true; // Ending the game
            }
            case CHOICE_NO -> System.out.println("You decided to keep fighting.");
            default -> System.out.println("Invalid choice");
        }
    }

    /**
     * Handles the Crystal Event When You Have a Bow
     */
    private static void handleEndCrystalEventWithBow() {
        System.out.println("Please Remove All End Crystals:");
        System.out.println("You have a Bow.");
        System.out.println("Remove all End Crystals using the Bow?");
        final int useBowChoice = GameHandler.makeChoiceYesNo();
        switch (useBowChoice) {
            case CHOICE_YES -> {
                System.out.println("All End Crystals have been removed.");
                phaseOneHasCompleted = true;
            }
            case CHOICE_NO -> System.out.println("You chose not to use the Bow.");
            default -> System.out.println("Invalid choice.");
        }
    }

    /**
     * Handles the Crystal Event When You Don't Have a Bow
     */
    private static void handleEndCrystalEventNoBow(final List<Card> hand) {
        final boolean hasSword = GameHandler.checkForCardInHand(hand, "EnchantedNetheriteSword");

        if (hasSword) {
            System.out.println("You have an EnchantedNetheriteSword.");
            System.out.println("End The Game using the EnchantedNetheriteSword?");
            final int useSwordChoice = GameHandler.makeChoiceYesNo();
            switch (useSwordChoice) {
                case CHOICE_YES -> handleOHKOEvent(); // End the game with the sword
                case CHOICE_NO -> {
                    System.out.println("You decided to keep fighting.");
                    handleEndCrystalEventWithBow();
                }
                default -> System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("You don't have the necessary items to continue.");
        }
    }

    /**
     * Prints the hand details.
     */
    private static void printHandDetails(final List<Card> hand) {
        hand.forEach(card -> System.out.println(card.toString()));
    }
}
