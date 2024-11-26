package ca.bcit.comp2522.TermProject.MyGame;

public class ActivatableCard implements Card
{
    private final String name;

    public ActivatableCard(final String name)
    {
        this.name = name;
    }

    @Override
    public void printDetails()
    {
        System.out.println(name + " (Activatable)");
    }

    @Override
    public String toString()
    {
        return name + " (Activatable)";
    }
}
