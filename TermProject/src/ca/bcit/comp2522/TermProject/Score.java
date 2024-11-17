package ca.bcit.comp2522.TermProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score
{
    private final LocalDateTime currentTime;
    private final DateTimeFormatter formatter;
    private final String formattedDateTime;

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

        this.numGamesPlayed = numGamesPlayed;
        this.numCorrectFirstAttempt = numCorrectFirstAttempt;
        this.numCorrectSecondAttempt = numCorrectSecondAttempt;
        this.numIncorrectTwoAttempts = numIncorrectTwoAttempts;
    }

    /**
     * Getter function for the formattedDateTime
     * @return the formatted date time as a String
     */
    public String getFormattedDateTime()
    {
        return formattedDateTime;
    }

    public static void main(String[] args)
    {
        Score s = new Score(3, 3, 3, 3);
        System.out.println(s.getFormattedDateTime());
    }
}
