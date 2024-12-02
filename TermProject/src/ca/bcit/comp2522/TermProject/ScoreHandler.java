package ca.bcit.comp2522.TermProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScoreHandler
{
    private final static String OUTPUT_DIRECTORY = "./";
    private final static int STARTING_VALUE = 0;
    private final static int RIGHT_SIDE = 1;
    private final static int DATE_TIME_INDEX_START = 15;

    /**
     * Appends the Score object to the file.
     * @param score as a Score object to read from.
     * @param outputFileName as the outputFile to create.
     * @throws IOException if there are file reading errors.
     */
    public static void appendScoreToFile(final Score score,
                                         final String outputFileName) throws IOException
    {
        final Path outputPath;
        final Path outputFile;

        outputPath = Paths.get(OUTPUT_DIRECTORY);
        outputFile = Paths.get(OUTPUT_DIRECTORY, outputFileName);

        if (Files.notExists(outputPath))
        {
            Files.createDirectories(outputPath);
        }

        if (Files.notExists(outputFile))
        {
            Files.createFile(outputFile);
        }

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
        final List<Score> scoreList;
        final DateTimeFormatter formatter;
        final Path inputFile;

        String dateTimeString;
        LocalDateTime dateTime;

        scoreList = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        inputFile = Paths.get(inputFileName);

        try (final BufferedReader br = Files.newBufferedReader(inputFile)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Date and Time: ")) {
                    // Extract the date and time
                    dateTimeString = line.substring(DATE_TIME_INDEX_START).trim();
                    dateTime = LocalDateTime.parse(dateTimeString, formatter);

                    // Extract the rest.
                    scoreList.add(createScoreFromParsing(br, dateTime));
                }
            }
        }
        return scoreList;
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
     * Handles creating a score object by reading the lines.
     * @param line as a String
     * @param br as a BufferedReader object.
     * @param dateTime as a LocalTime object
     * @return a score parsed from the fie.
     * @throws IOException if a file cannot be found.
     */
    private static Score createScoreFromParsing(final BufferedReader br, final LocalDateTime dateTime) throws IOException
    {
        final Score temp;
        String line;

        // Read the next lines to extract other information
        int numGamesPlayed = STARTING_VALUE;
        int numCorrectFirstAttempt = STARTING_VALUE;
        int numCorrectSecondAttempt = STARTING_VALUE;
        int numIncorrectTwoAttempts = STARTING_VALUE;

        while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
            if (line.startsWith("Games Played:")) {
                numGamesPlayed = Integer.parseInt(line.split(":")[RIGHT_SIDE].trim());
            } else if (line.startsWith("Correct First Attempts:")) {
                numCorrectFirstAttempt = Integer.parseInt(line.split(":")[RIGHT_SIDE].trim());
            } else if (line.startsWith("Correct Second Attempts:")) {
                numCorrectSecondAttempt = Integer.parseInt(line.split(":")[RIGHT_SIDE].trim());
            } else if (line.startsWith("Incorrect Attempts:")) {
                numIncorrectTwoAttempts = Integer.parseInt(line.split(":")[RIGHT_SIDE].trim());
            }
        }

        temp = new Score(dateTime,
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts);

        return temp;
    }

}
