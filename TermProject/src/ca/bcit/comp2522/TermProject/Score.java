package ca.bcit.comp2522.TermProject;

import java.io.File;
import java.nio.file.Files;
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

    private static final String OUTPUT_DIRECTORY = "src";
    private static final String OUTPUT_FILE = "src/score.txt";

    private int numGamesPlayed;
    private int numCorrectFirstAttempt;
    private int numCorrectSecondAttempt;
    private int numIncorrectTwoAttempts;

    public Score(final int numGamesPlayed,
                 final int numCorrectFirstAttempt,
                 final int numCorrectSecondAttempt,
                 final int numIncorrectTwoAttempts)
    {
        currentTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = currentTime.format(formatter);
        scoreList = new ArrayList<>();

        this.numGamesPlayed = numGamesPlayed;
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }

    /*
     * Getter function for the formattedDateTime
     * @return the formatted date time as a String
     */
    private String getFormattedDateTime()
    {
        return formattedDateTime;
    }

    public static void main(String[] args)
    {
        Score s = new Score(3, 3, 3, 3);
        System.out.println(s.getFormattedDateTime());
    }

    /*
     * Writes the score to the file.
     * @param outputFilePath
     */
    private void writeToScoreFile(final File outputFilePath)
    {
        Files.write(outputFilePath, scoreList, StandardOpenOption.APPEND);
    }
}
