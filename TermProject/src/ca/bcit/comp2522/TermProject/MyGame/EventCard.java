package ca.bcit.comp2522.TermProject.MyGame;

public class EventCard implements Card
{
    private final String name;

    public EventCard(final String name)
    {
        this.name = name;
    }

    @Override
    public void printDetails()
    {
        System.out.println(name + " (Event)");
    }

    @Override
    public String toString()
    {
        return name + " (Event)";
    }
}
