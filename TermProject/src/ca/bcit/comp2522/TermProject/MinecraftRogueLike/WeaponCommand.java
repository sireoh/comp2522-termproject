package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Handles the playing of weapon cards
 * @author Vincent Fung
 * @version 2024
 */
public class WeaponCommand implements CardCommand
{
    private final WeaponCard weaponCard;
    private final static CardInvoker invoker;

    static
    {
        // Main Remote
        invoker = new CardInvoker();
    }

    /**
     * WeaponCommand constructor.
     * @param weaponCard as a WeaponCard
     */
    public WeaponCommand(final WeaponCard weaponCard)
    {
        this.weaponCard = weaponCard;
    }

    /**
     * Executes the weapon card's functions.
     */
    @Override
    public void execute()
    {
        weaponCard.play();
    }

    /*
     * Sets the command to do and invokes it immediately.
     * @param weaponCommand as the command to do.
     */
    public static void playWeaponCard(final WeaponCommand weaponCommand)
    {
        invoker.setCommand(weaponCommand);
        GameHandler.handlePlayWeaponCard(invoker);
    }
}
