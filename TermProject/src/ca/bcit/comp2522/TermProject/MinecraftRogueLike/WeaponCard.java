package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Represents a card that represents a weapon in the game.
 * @author Vincent Fung
 * @version 2024
 */
public class WeaponCard extends Card {
    /**
     * Constructs a {@code WeaponCard} with the specified name.
     * @param name the name of the weapon card
     */
    public WeaponCard(final String name) {
        super(name);
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
        return super.getName() + " (Weapon)";
    }
}
