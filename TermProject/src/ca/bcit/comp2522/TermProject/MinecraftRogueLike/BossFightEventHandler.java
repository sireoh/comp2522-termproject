package ca.bcit.comp2522.TermProject.MinecraftRogueLike;
import java.util.List;

/**
 * BossFightEventHandler handles all the boss fighting logic
 * @author Vincent Fung
 * @version 2024
 */
public class BossFightEventHandler
{
    private static boolean hasBossFightStarted;
    private static boolean gameHasEnded;
    private static boolean userHasWon;
    private static int round;
    private final static int BOSS_FIGHT_STARTING_ROUND = 1;
    private final static WeaponCommand playOHKOSword;
    private final static WeaponCommand playBow;
    private final static WeaponCard OHKOSword;
    private final static WeaponCard bow;

    static
    {
        // Weapons
        OHKOSword = new WeaponCard("EnchantedNetheriteSword");
        bow = new WeaponCard("Bow");

        // Card Actions
        playOHKOSword = new WeaponCommand(OHKOSword);
        playBow = new WeaponCommand(bow);

        hasBossFightStarted = true;
        gameHasEnded = false;
        userHasWon = false;
        round = BOSS_FIGHT_STARTING_ROUND;
    }

    /**
     * Boolean function that checks if the boss fight has started.
     * @return the boolean to indicate the boss fight start.
     */
    public static boolean hasBossFightStarted()
    {
        return hasBossFightStarted;
    }

    /**
     * Boolean function that checks if the boss fight has ended.
     * @return the boolean to indicate the boss fight end.
     */
    public static boolean hasGameHasEnded()
    {
        return gameHasEnded;
    }

    /**
     * Boolean function that checks if the boss user has won.
     * @return the boolean to indicate the boss fight end.
     */
    public static boolean hasUserWon()
    {
        return userHasWon;
    }

    /**
     * Tells the game that the user has one.
     */
    public static void winGame()
    {
        userHasWon = true;
    }

    /**
     * Tells the game that the user has one.
     */
    public static void endGame()
    {
        gameHasEnded = true;
    }

    /**
     * Starting function that begins the boss fight from other
     * objects.
     */
    public static void beginBossFight() {
        hasBossFightStarted = true;
        System.out.println("Boss fight has begun!!!");
    }

    /**
     * Helper function handles the boss fight loop.
     */
    public static void handleBossFightLoop(final List<Card> hand)
    {
        final boolean hasSword;
        final boolean hasBow;

        hasSword = GameHandler.checkForCardInHand(hand, "EnchantedNetheriteSword");
        hasBow = GameHandler.checkForCardInHand(hand, "Bow");

        if (hasSword && hasBow)
        {
            System.out.println("You have an EnchantedNetheriteSword.");
            System.out.println("Do you want to use the Sword to end the game?");
            WeaponCommand.playWeaponCard(playOHKOSword);

            if (!gameHasEnded)
            {
                System.out.println("You have a Bow.");
                System.out.println("Remove all End Crystals using the Bow?");
                WeaponCommand.playWeaponCard(playBow);
            }
        }
        else if (hasSword)
        {
            System.out.println("You have an EnchantedNetheriteSword.");
            System.out.println("Do you want to use the Sword to end the game?");
            WeaponCommand.playWeaponCard(playOHKOSword);
        }
        else if (hasBow)
        {
            System.out.println("You have a Bow.");
            System.out.println("Remove all End Crystals using the Bow?");
            WeaponCommand.playWeaponCard(playBow);
        }
        else
        {
            System.out.println("You have nothing that can defeat the EnderDragon.");
        }

        handleGameEnd();
    }

    /*
     * Handles if the user won or lost.
     */
    private static void handleGameEnd()
    {
        if (userHasWon)
        {
            System.out.println("You Win!");
        } else {
            System.out.println("You have lost.");
        }
    }
}
