package ca.bcit.comp2522.TermProject;

public class Test {
    public static void main(String[] args)
    {
        Score score = new Score();
        score.setNumCorrectFirstAttempt(22);
        score.setNumCorrectSecondAttempt(88);
        score.setNumIncorrectTwoAttempts(22);

        for (int i = 0; i < 7; i++) {
            score.incrementGamesPlayed();
        }

        score.createScoreFile();
    }
}