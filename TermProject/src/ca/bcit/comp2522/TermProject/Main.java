package ca.bcit.comp2522.TermProject;
import ca.bcit.comp2522.TermProject.MinecraftRogueLike.MinecraftRogueLike;
import javafx.application.Platform;

import java.util.Scanner;

/**
 * Main Class, represents the Main Room of the Game.
 * @author Vincent Fung
 * @version 2024
 */
public class Main
{
    final static Scanner scan;

    static
    {
        scan = new Scanner(System.in);
    }

    public static void main(final String[] args)
    {
        Platform.setImplicitExit(false);

        boolean isStillPlaying;
        String option;

        isStillPlaying = true;
        WordGame wg;
        MinecraftRogueLike mcrl;

        do
        {
            System.out.println("Please choose an option:");
            System.out.println("Press W to play the Word game.");
            System.out.println("Press N to play the Number game.");
            System.out.println("Press M to play the MinecraftRogueLike game.");
            System.out.println("Press Q to quit.");

            option = makeChoice();

            switch(option)
            {
                case "w" -> {
                    System.out.println("Starting the word game.");
                    wg = new WordGame();
                }
                case "n" -> {
                    System.out.println("Starting the number game.");
                    JavaFXManager.getInstance().startNumberGame();
                }
                case "m" -> {
                    System.out.println("Starting the minecraft game.");
                    mcrl = new MinecraftRogueLike();
                }
                case "q" -> {
                    System.out.println("Thanks for playing !!!");
                    isStillPlaying = false;
                }
                default -> System.out.println("please enter a valid character.");
            }
        } while (isStillPlaying);
    }

    /**
     * Helper function that creates the choice menu.
     *
     * @return the String representing the user's decision
     */
    private static String makeChoice() {
        String option;

        while (true) {
            System.out.print("Enter your choice (W/N/M/Q): ");

            option = scan.nextLine().trim();

            if (option.equalsIgnoreCase("w") ||
                    option.equalsIgnoreCase("n") ||
                    option.equalsIgnoreCase("m") ||
                    option.equalsIgnoreCase("q")) {
                return option.toLowerCase();
            } else {
                System.out.println("Invalid input. Please choose either W, N, M, or Q.");
            }
        }
    }
}
