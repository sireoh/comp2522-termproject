package ca.bcit.comp2522.TermProject.MyGame;

public class WeaponCard implements Card
{
    private final String name;

    public WeaponCard(final String name)
    {
        this.name = name;
    }

    @Override
    public void printDetails()
    {
        System.out.println(name + " (Weapon)");
    }

    @Override
    public String toString()
    {
        return name + " (Weapon)";
    }
}
