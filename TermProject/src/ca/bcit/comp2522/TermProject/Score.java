package ca.bcit.comp2522.TermProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Score
{
    private final LocalDateTime currentTime;
    private final DateTimeFormatter formatter;
    private final String formattedDateTime;
    private final List<String> scoreList;
    private final List<Integer> pastTotalScoresList;
    private final static int STARTING_SCORE = 0;

    private static final String OUTPUT_DIRECTORY = "output/";
    private static final String OUTPUT_FILE = "output/score.txt";

    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;
    private static final int NUM_CORRECT_FIRST_ATTEMPT_MULTIPLIER = 2;

    public Score()
    {
        currentTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = currentTime.format(formatter);
        pastTotalScoresList = new ArrayList<>();
        scoreList = new ArrayList<>();

        this.numGamesPlayed = STARTING_SCORE;
        this.numCorrectFirstAttempt = STARTING_SCORE;
        this.numCorrectSecondAttempt = STARTING_SCORE;
        this.numIncorrectTwoAttempts = STARTING_SCORE;
    }

    /*
     * Helper function for the formattedDateTime
     * @return the formatted date time as a String
     */
    private String getFormattedDateTime()
    {
        return formattedDateTime;
    }

    /*
     * Inserts all necessary data into the scoreList ArrayList.
     * To be printed later.
     */
    private void setupScoreList() {
        final int currentTotalScore = calcTotalScore();
        pastTotalScoresList.add(currentTotalScore); // Add the current score to the list

        scoreList.clear(); // Clear the scoreList to avoid appending old data
        scoreList.add("Date and Time: " + getFormattedDateTime());
        scoreList.add("Games Played: " + numGamesPlayed);
        scoreList.add("Correct First Attempts: " + numCorrectFirstAttempt);
        scoreList.add("Correct Second Attempts: " + numCorrectSecondAttempt);
        scoreList.add("Incorrect Attempts: " + numIncorrectTwoAttempts);
        scoreList.add("Total Score: " + currentTotalScore);
        scoreList.add("Average Score: " + calcAverageScore()); // Calculate and display the average
    }

    /*
     * Calculates the Average Score.
     * @return the average score as an int.
     */
    private int calcAverageScore()
    {
        int total;
        total = STARTING_SCORE;
        for (final int score : pastTotalScoresList)
        {
            total += score;
        }

        return total / pastTotalScoresList.size();
    }

    /*
     * Calculates the total score.
     * @return the totalScore as an int.
     */
    private int calcTotalScore()
    {
        int total;
        total = ((numCorrectFirstAttempt) * NUM_CORRECT_FIRST_ATTEMPT_MULTIPLIER) +
                numCorrectSecondAttempt;

        return total;
    }

    /*
     * Writes the score to the file.
     * @param outputFilePath
     */
    private void writeToScoreFile(final Path outputFilePath) {
        setupScoreList(); // Set up the score list with current stats

        try {
            // Append the score data to the file
            Files.write(outputFilePath, scoreList, StandardOpenOption.APPEND);
            Files.writeString(outputFilePath, System.lineSeparator(), StandardOpenOption.APPEND);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Increments the numGamesPlayed.
     */
    public void incrementGamesPlayed()
    {
        this.numGamesPlayed++;
    }

    /**
     * Sets the numCorrectFirstAttempt.
     * @param numCorrectFirstAttempt as an int.
     */
    public void setNumCorrectFirstAttempt(final int numCorrectFirstAttempt)
    {
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
    }

    /**
     * Sets the numCorrectSecondAttempt.
     * @param numCorrectSecondAttempt as an int.
     */
    public void setNumCorrectSecondAttempt(final int numCorrectSecondAttempt)
    {
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
    }

    /**
     * Sets the numIncorrectTwoAttempts.
     * @param numIncorrectTwoAttempts as an int.
     */
    public void setNumIncorrectTwoAttempts(final int numIncorrectTwoAttempts)
    {
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }

    /**
     * Create the Score file, if it hasn't been created already.
     */
    public void createScoreFile() {
        final Path scoreFileDirectory = Paths.get(OUTPUT_DIRECTORY);
        final Path scoreFile = Paths.get(OUTPUT_FILE);

        // Create directory if it doesn't exist
        if (Files.notExists(scoreFileDirectory)) {
            try {
                Files.createDirectory(scoreFileDirectory);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Create file if it doesn't exist
        if (Files.notExists(scoreFile)) {
            try {
                Files.createFile(scoreFile);
                System.out.println("Output file created. Writing initial score data...");
                // Only write initial score when the file is first created
                setupScoreList(); // Prepare the current data
                writeToScoreFile(scoreFile); // Write initial data to the file
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Output file already exists. Appending new score...");
        }

        // Append new score data (Do not write initial score again)
        setupScoreList(); // Prepare the current data
        writeToScoreFile(scoreFile); // Append the updated score to the file
    }

}
