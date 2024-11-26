package ca.bcit.comp2522.TermProject.MyGame;

public class TokenCard implements Card
{
    private final String name;

    public TokenCard(final String name)
    {
        this.name = name;
    }

    @Override
    public void printDetails()
    {
        System.out.println(name + " (Token)");
    }
}
