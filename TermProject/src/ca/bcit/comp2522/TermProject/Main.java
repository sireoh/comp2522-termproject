package ca.bcit.comp2522.TermProject;

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
                    case "w" : System.out.println("starting the word game."); break;
                    case "n" : System.out.println("starting the number game."); break;
                    case "m" : System.out.println("starting the minecraft game."); break;
                    default : System.out.println("please enter a valid character."); break;
                }
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
            System.out.println("Chosen option can not be null or Blank.");
            return false;
        }

        if (
                !(option.equalsIgnoreCase("W") ||
                option.equalsIgnoreCase("N") ||
                option.equalsIgnoreCase("M") ||
                option.equalsIgnoreCase("Q"))
        )
        {
            System.out.println("Chosen option must be either W, N, M or Q.");
            return false;
        }

        return true;
    }
}
