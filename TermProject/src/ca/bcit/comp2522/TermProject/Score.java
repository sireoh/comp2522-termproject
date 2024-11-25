package ca.bcit.comp2522.TermProject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Score Object, represents a Score.
 * @author Vincent Fung
 * @version 2024
 */
public class Score
{
    private final DateTimeFormatter formatter;
    private final String formattedDateTime;

    private final int numGamesPlayed;
    private final int numCorrectFirstAttempt;
    private final int numCorrectSecondAttempt;
    private final int numIncorrectTwoAttempts;

    private static final int NUM_CORRECT_FIRST_ATTEMPT_MULTIPLIER = 2;
    private static final int STARTING_VALUE = 0;

    /**
     * Score constructor.
     * @param currentTime as a LocalDateTime
     * @param numGamesPlayed as an int.
     * @param numCorrectFirstAttempt as an int.
     * @param numCorrectSecondAttempt as an int.
     * @param numIncorrectTwoAttempts as an int.
     */
    public Score(final LocalDateTime currentTime,
                final int numGamesPlayed,
                 final int numCorrectFirstAttempt,
                 final int numCorrectSecondAttempt,
                 final int numIncorrectTwoAttempts)
    {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = currentTime.format(formatter);

        this.numGamesPlayed = numGamesPlayed;
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }

    /**
     * Empty Constructor.
     */
    public Score(){
        final LocalDateTime currentTime;

        currentTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = currentTime.format(formatter);

        this.numGamesPlayed = STARTING_VALUE;
        this.numCorrectFirstAttempt = STARTING_VALUE;
        this.numCorrectSecondAttempt = STARTING_VALUE;
        this.numIncorrectTwoAttempts = STARTING_VALUE;
    }

    /**
     * Helper function for the formattedDateTime
     * @return the formatted date time as a String
     */
    public String getFormattedDateTime(){ return formattedDateTime; }

    /**
     * Gets the Number of Games Played.
     * @return the number of games played as an int.
     */
    public int getNumGamesPlayed(){ return numGamesPlayed; }

    /**
     * Gets the number of questions answered
     * correctly on the first attempt
     * @return the number representing the
     * correct answers as an int.
     */
    public int getNumCorrectFirstAttempt(){ return numCorrectFirstAttempt; }

    /**
     * Gets the number of questions answered
     * correctly on the second attempt
     * @return the number representing the
     * correct answers as an int.
     */
    public int getNumCorrectSecondAttempt(){ return numCorrectSecondAttempt; }

    /**
     * Gets the number of questions answered
     * incorrectly after all attempts used.
     * @return the number representing the incorrect
     * answers as an int.
     */
    public int getNumIncorrectTwoAttempts(){ return numIncorrectTwoAttempts; }

    /*
     * Calculates the total score.
     * @return the totalScore as an int.
     */
    public int getScore(){
        int total;
        total = ((numCorrectFirstAttempt) * NUM_CORRECT_FIRST_ATTEMPT_MULTIPLIER) +
                numCorrectSecondAttempt;

        return total;
    }

    /**
     * Writes the score to the file.
     *
     * @param score      as a Score object.
     * @param outputFileName as a String.
     */
    public static void appendScoreToFile(final Score score, final String outputFileName) throws IOException
    {
        ScoreHandler.appendScoreToFile(score, outputFileName);
    }

    /**
     * Reads the scores from the File, and stores it as a Score Array
     * @param inputFileName as a String.
     * @return List<Score> of all the Scores in the file.
     * @throws IOException if file could not be parsed.
     */
    public static List<Score> readScoresFromFile(final String inputFileName) throws IOException
    {
        return ScoreHandler.readScoresFromFile(inputFileName);
    }

    /**
     * The toString method for Score
     * @return the details of Score as a String.
     */
    @Override
    public String toString()
    {
        final LocalDateTime dateTime;
        dateTime = LocalDateTime.now();
        return String.format(
                "Date and Time: %s\n" +
                "Games Played: %d\n" +
                "Correct First Attempts: %d\n" +
                "Correct Second Attempts: %d\n" +
                "Incorrect Attempts: %d\n" +
                "Score: %d points\n",
                dateTime.format(formatter),
                numGamesPlayed,
                numCorrectFirstAttempt,
                numCorrectSecondAttempt,
                numIncorrectTwoAttempts,
                getScore()
        );
    }
}
