package ca.bcit.comp2522.TermProject;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        final Scanner scan;
        scan = new Scanner(System.in);

        String option;

       do
       {
           System.out.println("Please choose an option:\n");
           System.out.println("Press W to play the Word game.\n");
           System.out.println("Press N to play the Number game.\n");
           System.out.println("Press M to play the Minecraft game.\n");
           System.out.println("Press Q to quit.\n");

           option = scan.nextLine();

           if (validateChoice(option))
           {

           }
       } while (!option.equalsIgnoreCase("Q") && !validateChoice(option));

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
