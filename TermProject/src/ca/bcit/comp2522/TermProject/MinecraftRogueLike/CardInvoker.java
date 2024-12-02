package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * CardInvoker is the remote control that runs the cards,
 * internal functions
 * @author Vincent Fung
 * @version 2024
 */
public class CardInvoker
{
    private CardCommand command;

    /**
     * Constructor sets the command
     * @param command as a CardCommand.
     */
    public void setCommand(final CardCommand command)
    {
        this.command = command;
    }

    /**
     * Invokes the card and runs the commands function.
     */
    public void invokeCard()
    {
        command.execute();
    }
}
