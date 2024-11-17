package ca.bcit.comp2522.TermProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * World class, represents a World.
 * @author Vincent Fung
 * @version 2024
 */
public class World
{
    private final Map<String, Country> countries;
    private final static String COUNTRY_FILE_NAMES = "abcdefghijklmnopqrstuvyz";
    private final static int STARTING_INDEX = 0;
    private final static int NAME_INDEX = 0;
    private final static int NAME_AND_CAPITAL_SIZE = 2;
    private final static int CAPITAL_INDEX = 1;
    private final static int FACTS_SIZE = 3;
    /**
     * World constructor.
     */
    public World()
    {
        countries = new HashMap<>();
        generateWorld(countries);
    }

    /**
     * Gets the countries Hashmap.
     * @return the countries as a Map.
     */
    public Map<String, Country> getCountries()
    {
        return countries;
    }

    /*
     * Parses the country and puts it into the country array.
     * @param countryPath as a Path.
     * @param countries as a Hashmap.
     */
    private static void parseCountry(final Path countryPath, final Map<String, Country> countries)
    {
        try (final BufferedReader br = Files.newBufferedReader(countryPath))
        {
            String currentLine;
            currentLine = br.readLine();

            while ((currentLine) != null)
            {
                String[] nameAndCapital;
                String[] factList;
                nameAndCapital = new String[] {};
                factList = new String[FACTS_SIZE];

                // Parse the name and capital.
                if (currentLine.contains(":"))
                {
                    nameAndCapital = currentLine.split(":");
                } else {
                    currentLine = br.readLine();
                    continue;
                }

                // If the name and capital isn't populated correctly
                // Keep moving down the list.
                if (nameAndCapital.length < NAME_AND_CAPITAL_SIZE) {
                    currentLine = br.readLine();
                    continue;
                }

                // Fill in the facts array.
                for (int i = STARTING_INDEX; i < FACTS_SIZE; i++)
                {
                    String fact = br.readLine();
                    if (fact != null) { factList[i] = fact; }
                }

                Country c = new Country(nameAndCapital[NAME_INDEX], nameAndCapital[CAPITAL_INDEX], factList);
                countries.put(nameAndCapital[NAME_INDEX], c);

                currentLine = br.readLine();
            }
        }
        catch (final IOException e)
        {
            System.out.println("Error reading file: " + countryPath);
            e.printStackTrace();
        }
    }


    /*
     * Helper function that generates the countries hashmap.
     * @param countries as a Hashmap of String, and Country
     */
    private static void generateWorld(final Map<String, Country> countries)
    {
        IntStream.range(STARTING_INDEX, COUNTRY_FILE_NAMES.length())
                .mapToObj(i -> "src/ca/bcit/comp2522/TermProject/" + COUNTRY_FILE_NAMES.charAt(i) + ".txt")
                .map(Path::of)
                .forEach(countryPath -> {
                    parseCountry(countryPath, countries);
                });
    }
}
