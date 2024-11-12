package ca.bcit.comp2522.TermProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * World class, represents a World.
 * @author Vincent Fung
 * @version 2024
 */
public class World
{
    private final Map<String, Country> countries;
    private final Set<String> keyList;
    private final static String COUNTRY_FILE_NAMES = "abcdefghijklmnopqrstuvyz";

    /**
     * World constructor.
     */
    public World()
    {
        countries = new HashMap<>();
        generateWorld(countries);

        keyList = countries.keySet();

        for (final String key : keyList)
        {
            final Country country;
            country = countries.get(key);

            System.out.println(country.getDetails());
        }
    }

    /*
     * Parses the country and puts it into the country array.
     * @param countryPath as a Path.
     * @param countries as a Hashmap.
     */
    private static void parseCountry(final Path countryPath, final Map<String, Country> countries)
    {
        // Using try-with-resources (large file) - to be auto-closed, resource needs to be declared inside try block
        try (final BufferedReader br = Files.newBufferedReader(countryPath))
        {
            // Skip the first line if it's a header or unnecessary
            String currentLine = br.readLine(); // Skip header or empty line

            // Read the country data while the file has more lines.
            while ((currentLine = br.readLine()) != null)
            {
                // Check if the line contains a colon, indicating a name:capital pair
                if (currentLine.contains(":"))
                {
                    // Split the line into name and capital based on the colon delimiter
                    String[] nameAndCapital = currentLine.split(":");

                    // Ensure that we have two parts: name and capital
                    if (nameAndCapital.length == 2)
                    {
                        // Fetch the next three lines for facts, checking if they are valid
                        String[] facts = new String[3];
                        for (int i = 0; i < 3; i++)
                        {
                            String factLine = br.readLine();
                            if (factLine != null && !factLine.trim().isEmpty())
                            {
                                facts[i] = factLine.trim();
                            }
                            else
                            {
                                // If any of the facts are missing, set as empty or handle error
                                facts[i] = "Unknown Fact";
                            }
                        }

                        // Create a new Country object and add it to the countries map
                        Country c = new Country(nameAndCapital[0], nameAndCapital[1], facts);
                        countries.put(nameAndCapital[0], c);
                    }
                    else
                    {
                        System.out.println("Skipping malformed country line: " + currentLine);
                    }
                }
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
        IntStream.range(0, COUNTRY_FILE_NAMES.length())
                .mapToObj(i -> "src/ca/bcit/comp2522/TermProject/" + COUNTRY_FILE_NAMES.charAt(i) + ".txt")
                .map(Path::of)
                .forEach(countryPath -> {
                    parseCountry(countryPath, countries);
                });
    }
}
