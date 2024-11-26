package ca.bcit.comp2522.TermProject.MinecraftRogueLike;

/**
 * Card interface, represents a card.
 * @author Vincent Fung
 * @version 2024
 */
public abstract class Card {
    private final String name;

    Card(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    abstract void printDetails();
}