package ca.bcit.comp2522.TermProject;

import java.util.Random;

/**
 * Country class, represents a Country.
 * @author Vincent Fung
 * @version 2024
 */
public class Country
{
    private final String name;
    private final String capitalCityName;
    private final String[] facts;
    private final static int FIRST_INDEX = 0;
    private final static int SECOND_INDEX = 1;
    private final static int THIRD_INDEX = 2;

    /**
     * Constructor for the Country class.
     * @param name as a String.
     * @param capitalCityName as a String.
     * @param facts as a String array.
     */
    public Country(final String name, final String capitalCityName, final String[] facts)
    {
        validateName(name);
        validateCapitalCityName(capitalCityName);

        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    /**
     * Gets the name of the Country.
     * @return the name as a String.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the Capital City name of the Country
     * @return the Capital City Name as a String.
     */
    public String getCapitalCityName()
    {
        return capitalCityName;
    }

    /**
     * Generates a random fact from the Country.
     * @return a random fact as a String.
     */
    public String getRandomFact()
    {
        final Random random;
        final int randIndex;

        random = new Random();
        randIndex = random.nextInt(facts.length);

        return facts[randIndex];
    }

    /**
     * Gets the details of the Country.
     * @return the details as a String.
     */
    public String getDetails()
    {
        return name + ", "
            + capitalCityName + "\n"
            + facts[FIRST_INDEX] + "\n"
            + facts[SECOND_INDEX] + "\n"
            + facts[THIRD_INDEX] + "\n";
    }

    /*
     * Validates if the name is null, or blank.
     * @param name as a String.
     */
    private static void validateName(final String name)
    {
        if (name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    /*
     * Validates if the capitalCityName is null, or blank.
     * @param capitalCityName as a String.
     */
    private static void validateCapitalCityName(final String capitalCityName)
    {
        if (capitalCityName == null || capitalCityName.isBlank())
        {
            throw new IllegalArgumentException("capitalCityName cannot be null or empty");
        }
    }
}
