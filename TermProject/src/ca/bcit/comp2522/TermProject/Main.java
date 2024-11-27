package ca.bcit.comp2522.TermProject;

import ca.bcit.comp2522.TermProject.MinecraftRogueLike.MinecraftRogueLike;

import java.util.Scanner;

/**
 * Main Class, represents the Main Room of the Game.
 * @author Vincent Fung
 * @version 2024
 */
public class Main
{
    private final static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args)
    {
        final Scanner scan;
        final NumberGame ng;
        boolean isStillPlaying;

        isStillPlaying = true;
        WordGame wg;
        MinecraftRogueLike mcrl;
        scan = new Scanner(System.in);

       do
       {
           System.out.println("Please choose an option:");
           System.out.println("Press W to play the Word game.");
           System.out.println("Press N to play the Number game.");
           System.out.println("Press M to play the Minecraft game.");
           System.out.println("Press Q to quit.");

           switch(makeChoice())
           {
               case "w" -> {
                   System.out.println("Starting the word game.");
                   wg = new WordGame();
               }
               case "n" -> {
                   System.out.println("Starting the number game.");
                   NumberGame.main(new String[]{});
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

        scan.close();
    }

    /*
     * Helper function that create the choice menu
     * @return the String representing the users decision
     */
    private static String makeChoice()
    {
        while(true)
        {
            final String option;
            option = scanner.nextLine().trim();
            if(!(option.equalsIgnoreCase("W") ||
                 option.equalsIgnoreCase("N") ||
                 option.equalsIgnoreCase("M") ||
                 option.equalsIgnoreCase("Q")))
            {
                return option;
            } else
            {
                System.out.println("Chosen option must be either W, N, M or Q.");
            }
        }
    }
}
