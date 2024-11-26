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
    public static void main(String[] args)
    {
        final Scanner scan;
        NumberGame ng;

        WordGame wg;
        MinecraftRogueLike mg;
        scan = new Scanner(System.in);

        String option;

       do
       {
           System.out.println("Please choose an option:");
           System.out.println("Press W to play the Word game.");
           System.out.println("Press N to play the Number game.");
           System.out.println("Press M to play the Minecraft game.");
           System.out.println("Press Q to quit.");

           option = scan.nextLine();
           option = option.toLowerCase();

           if (validateChoice(option) && !option.equals("q"))
           {
                switch(option)
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
                        mg = new MinecraftRogueLike();
                    }
                    default -> System.out.println("please enter a valid character.");
                }
           }
           else
           {
               System.out.println("Chosen option must be either W, N, M or Q.");
           }
       } while (!option.equalsIgnoreCase("Q") && !validateChoice(option));

        if (option.equalsIgnoreCase("q")) {
            System.out.println("Thanks for playing !!!");
        }

        scan.close();
    }

    /**
     * Validates if the input is blank, empty or does not contain
     * W, N, M or Q.
     * @param option as a string.
     * @return true only if it fails the check.
     */
    private static boolean validateChoice(final String option)
    {
        if (option == null || option.isBlank())
        {
            return false;
        }

        if (
                !(option.equalsIgnoreCase("W") ||
                option.equalsIgnoreCase("N") ||
                option.equalsIgnoreCase("M") ||
                option.equalsIgnoreCase("Q"))
        )
        {
            return false;
        }

        return true;
    }
}
