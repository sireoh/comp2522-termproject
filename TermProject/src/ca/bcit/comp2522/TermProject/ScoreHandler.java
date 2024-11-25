package ca.bcit.comp2522.TermProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreHandler
{
    private final static String OUTPUT_DIRECTORY = "output/";

    private final static int STARTING_VALUE = 0;
    private final static int RIGHT_SIDE = 0;

    /**
     * Appends the Score object to the file.
     * @param score as a Score object to read from.
     * @param outputFileName as the outputFile to create.
     * @throws IOException if there are file reading errors.
     */
    public static void appendScoreToFile(final Score score, final String outputFileName) throws IOException
    {
        final Path outputFile;
        final Path outputPath;

        outputPath = Paths.get(OUTPUT_DIRECTORY);
        outputFile = Paths.get(outputFileName);

        // Creates the output directory path, if it does not exist.
        if (Files.notExists(outputPath)){ Files.createDirectory(outputPath); }

        if (Files.notExists(outputFile)){ Files.createFile(outputFile); }

        Files.write(outputFile, createScoreList(score), StandardOpenOption.APPEND);
        Files.writeString(outputFile, "\n", StandardOpenOption.APPEND);
    }

    /**
     * Reads the scores from the File, and stores it as a Score Array
     * @param inputFileName as a String.
     * @return List<Score> of all the Scores in the file.
     * @throws IOException if file could not be parsed.
     */
    public static List<Score> readScoresFromFile(final String inputFileName) throws IOException
    {
        final List<Score> scoresList;
        final Path inputFile;
        Score score;

        inputFile = Paths.get(inputFileName);
        scoresList = new ArrayList<>();

        String dateAndTime;
        int numGamesPlayed;
        int numCorrectFirstAttempt;
        int numCorrectSecondAttempt;
        int numIncorrectTwoAttempts;

        dateAndTime = null;
        numGamesPlayed = STARTING_VALUE;
        numCorrectFirstAttempt = STARTING_VALUE;
        numCorrectSecondAttempt = STARTING_VALUE;
        numIncorrectTwoAttempts = STARTING_VALUE;

        // Parse the file to collect all the scores.
        if (Files.exists(inputFile)) {

            // Using try-with-resources (large file) - to be auto-closed, resource needs to be declared inside try block
            try (final BufferedReader br = Files.newBufferedReader(inputFile))
            {
                String currentLine;

                currentLine = br.readLine();

                // Check if the first and subsequent lines are not null.
                while (currentLine != null)
                {
                    if (currentLine.startsWith("Date and Time: ")) {
                        dateAndTime = currentLine.split("e: ")[RIGHT_SIDE];
                        continue;
                    }

                    if (currentLine.startsWith("Games Played: ")) {
                        numGamesPlayed = handleStringInts(currentLine, "d: ");
                        continue;
                    }

                    if (currentLine.startsWith("Correct First Attempts: ")) {
                        numCorrectFirstAttempt = handleStringInts(currentLine, "s: ");
                        continue;
                    }

                    if (currentLine.startsWith("Correct Second Attempts: ")) {
                        numCorrectSecondAttempt = handleStringInts(currentLine, "s: ");
                        continue;
                    }

                    if (currentLine.startsWith("Incorrect Attempts: ")) {
                        numIncorrectTwoAttempts = handleStringInts(currentLine, "s: ");
                        continue;
                    }

                    // Resets the variables and adds to score.
                    if (currentLine.equals("\n"))
                    {
                        if ((dateAndTime != null) &&
                                (numGamesPlayed != STARTING_VALUE) &&
                                (numCorrectFirstAttempt != STARTING_VALUE) &&
                                (numCorrectSecondAttempt != STARTING_VALUE) &&
                                (numIncorrectTwoAttempts != STARTING_VALUE)
                        ) {
                            score = new Score(Objects.requireNonNull(parseLocalDateTimeFromString(dateAndTime)),
                                    numGamesPlayed,
                                    numCorrectFirstAttempt,
                                    numCorrectSecondAttempt,
                                    numIncorrectTwoAttempts);

                            scoresList.add(score);
                        }

                        // Clears the data.
                        dateAndTime = null;
                        numGamesPlayed = STARTING_VALUE;
                        numCorrectFirstAttempt = STARTING_VALUE;
                        numCorrectSecondAttempt = STARTING_VALUE;
                        numIncorrectTwoAttempts = STARTING_VALUE;
                    }

                    currentLine = br.readLine();
                }
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        return scoresList;
    }

    /*
     * Returns a LocalDateTime object.
     * @param dateTime as the String to parse.
     * @return
     */
    private static LocalDateTime parseLocalDateTimeFromString(final String dateTime)
    {
        // Define the formatter for the input pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            // Parse the string to LocalDateTime
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date-time: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a score list, and returns it as a List<String>
     * @param score as the Score object to read.
     * @return a List<String> with all the parsed data.
     */
    private static List<String> createScoreList(final Score score)
    {
        final List<String> scoreList;
        scoreList = new ArrayList<>();

        scoreList.add("Date and Time: " + score.getFormattedDateTime());
        scoreList.add("Games Played: " + score.getNumGamesPlayed());
        scoreList.add("Correct First Attempts: " + score.getNumCorrectFirstAttempt());
        scoreList.add("Correct Second Attempts: " + score.getNumCorrectSecondAttempt());
        scoreList.add("Incorrect Attempts: " + score.getNumIncorrectTwoAttempts());
        scoreList.add("Score: " + score.getScore() + " points");

        return scoreList;
    }

    /*
     * Handles the Integers in String form,
     * and outputs it as an int.
     * @param linetoHandle as the String to perform parsing to.
     * @param regex as the expresson to split from.
     */
    private static int handleStringInts(final String linetoHandle, final String regex)
    {
        final String unparsedString;
        unparsedString = linetoHandle.split(regex)[RIGHT_SIDE];
        return Integer.parseInt(unparsedString);
    }

}
