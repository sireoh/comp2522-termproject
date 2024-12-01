package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card that represents a weapon in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class WeaponCard extends Card {
    private final int attackAccuracy;
    private final String cardDescription;
    private final static int MIN_ACCURACY = 30;
    private final static int MAX_ACCURACY = 100;
    private final static int EMPTY_ACCURACY = 0;
    private final static String CARD_BORDER_LINE = "+----------------------------------+\n";

    /**
     * Constructs a {@code WeaponCard} with the specified name.
     * @param name the name of the weapon card
     */
    public WeaponCard(final String name) {
        super(name);
        attackAccuracy = handleAccuracy(name);
        cardDescription = handleDescription(name);
    }

    /*
     * Handles the attack accuracy depending on what card it is.
     * @param name as the name of the card to set
     * @return the String the card should have
     */
    private String handleDescription(final String name)
    {
        if (name.equals("EnchantedNetheriteSword")) {
            return "OHKO's the dragon";
        } else {
            return "";
        }
    }

    /*
     * Handles the attack accuracy depending on what card it is.
     * @param name as the name of the card to set
     * @return the int the card should have
     */
    private int handleAccuracy(final String name)
    {
        return switch(name)
        {
            case "StoneSword" -> MIN_ACCURACY;
            case "Bow" -> MAX_ACCURACY;
            default -> EMPTY_ACCURACY;
        };
    }

    /**
     * Gets the attack accuracy
     * @return the attack accuracy as an int
     */
    public int getAttackAccuracy()
    {
        return attackAccuracy;
    }

    /**
     * Prints the details of the weapon card to the console.
     */
    @Override
    public void printDetails() {
        System.out.println(super.getName() + " (Weapon)");
    }

    /**
     * Returns a string representation of the weapon card.
     * @return a string describing the weapon card
     */
    @Override
    public String toString() {
        final StringBuilder sb;
        sb = new StringBuilder();

        sb.append(CARD_BORDER_LINE);
        sb.append(formatCard(super.getName())).append("\n");
        sb.append(formatCard("Weapon")).append("\n");
        sb.append(CARD_BORDER_LINE);

        if (attackAccuracy != EMPTY_ACCURACY) {
            sb.append(formatCard(attackAccuracy + "% ATK ACC")).append("\n");
        } else {
            sb.append(formatCard(cardDescription)).append("\n");
        }

        sb.append(CARD_BORDER_LINE);
        return sb.toString();
    }

}
