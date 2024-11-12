package ca.bcit.comp2522.TermProject;

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

    /**
     * Gets the details of the Country.
     * @return the details as a String.
     */
    public String getDetails()
    {
        return "The country is " + name + " and capital city is " + capitalCityName
                + "Some fun facts are " + facts[0] + ", " + facts[1] + ", and " + facts[2] + ".";
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
