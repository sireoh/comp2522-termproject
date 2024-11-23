package ca.bcit.comp2522.TermProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Word Game
 * @author Vincent Fung
 * @version 2024
 */
public class WordGame
{
    private final Map<String, Country> countries;
    private final Country[] questions;
    private final static int MAX_QUESTION_SIZE = 10;
    private final static int QUESTION_TYPE_RANDOM_FACT = 0;
    private final static int QUESTION_TYPE_WHICH_CAPITAL = 1;
    private final static int QUESTION_TYPE_WHICH_COUNTRY = 2;
    private final static int QUESTION_TYPE_SIZE = 3;
    private final static int START_INDEX = 0;
    private final static int STARTING_TOTAL = 0;
    private final static int MAX_TRIES = 3;
    private final static int END_INDEX_RANDOM_FACT_FIRST_LETTER = 1;
    private final static int CORRECT_ON_FIRST_ATTEMPT = 1;
    private final static int CORRECT_ON_SECOND_ATTEMPT = 2;
    private final static int INCORRECT_AFTER_TWO_ATTEMPTS = 3;
    private final static int INCREMENT_AMOUNT = 1;
    private final static int RIGHT_SIDE = 1;
    private final Map<Integer, Integer> attempts;
    private final Score score;
    private boolean isPlaying;
    private final Scanner scanner;
    private final Path inputFilePath;
    private static final String INPUT_FILE = "output/score.txt";
    private final List<Integer> totalScores;
    private final List<String> dateAndTimes;

    public WordGame()
    {

        final World world;
        attempts = new HashMap<>();
        attempts.put(CORRECT_ON_FIRST_ATTEMPT, START_INDEX);
        attempts.put(CORRECT_ON_SECOND_ATTEMPT, START_INDEX);
        attempts.put(INCORRECT_AFTER_TWO_ATTEMPTS, START_INDEX);
        scanner = new Scanner(System.in);
        isPlaying = true;
        String choice;

        inputFilePath = Paths.get(INPUT_FILE);
        world = new World();
        countries = world.getCountries();
        questions = generateQuestions();
        score = new Score();
        totalScores = new ArrayList<>();
        dateAndTimes = new ArrayList<>();

        printQuitMessage();

        while (isPlaying) {
            play();

            System.out.println("Thanks for playing !!");
            do {
                System.out.println("Play again? [y]es, [n]o");
                choice = scanner.nextLine();
            } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));

            switch (choice.toLowerCase())
            {
                case "y" -> score.incrementGamesPlayed();
                case "n" -> {
                    printQuitMessage();
                    score.createScoreFile();
                    isPlaying = false;
                }
                default -> System.out.println("Error.");
            }
        }

        scanner.close();
    }

    /*
     * Begins the WordGame.
     */
    private void play()
    {
        final Random random;
        final Scanner scan;

        // These get updated in the while loop.
        int currentQuestion;
        String answer;
        boolean isCorrect;
        int typeOfQuestion;
        int currentTry;
        int currentAttempt;
        String randomFact;

        random = new Random();
        scan = new Scanner(System.in);
        typeOfQuestion = random.nextInt(QUESTION_TYPE_SIZE);
        currentAttempt = START_INDEX;
        currentTry = START_INDEX;
        currentQuestion = START_INDEX;
        isCorrect = false;
        randomFact = questions[currentQuestion].getRandomFact();


        while (currentQuestion < MAX_QUESTION_SIZE) {
            currentTry++;
            System.out.println("Current Attempt: " + currentTry);

            System.out.println(
                    switch (typeOfQuestion) { // Ensure random number is between 0 and 2
                        case QUESTION_TYPE_RANDOM_FACT -> handleFactQuestion(randomFact);
                        case QUESTION_TYPE_WHICH_CAPITAL -> questions[currentQuestion].getCapitalCityName() + " is the capital of which country?";
                        case QUESTION_TYPE_WHICH_COUNTRY -> "What is the capital of " + questions[currentQuestion].getName() + "?";
                        default -> "No function called";
                    }
                    + "\t" + questions[currentQuestion].getName() + ", " + questions[currentQuestion].getCapitalCityName()
            );

            answer = scan.nextLine();

            switch (typeOfQuestion) { // Ensure random number is between 0 and 2
                case QUESTION_TYPE_RANDOM_FACT -> isCorrect = handleAnswer(currentQuestion, answer, typeOfQuestion);
                case QUESTION_TYPE_WHICH_CAPITAL -> isCorrect = handleAnswer(currentQuestion, answer, typeOfQuestion);
                case QUESTION_TYPE_WHICH_COUNTRY -> isCorrect = handleAnswer(currentQuestion, answer, typeOfQuestion);
                default -> throw new IllegalStateException("Unexpected value: " + typeOfQuestion);
            }

            if((isCorrect || currentTry >= MAX_TRIES)) {
                attempts.replace(currentTry, attempts.get(currentTry) + INCREMENT_AMOUNT);
                printScore(); // DEBUG

                typeOfQuestion = random.nextInt(QUESTION_TYPE_SIZE);
                randomFact = questions[currentQuestion].getRandomFact();
                currentQuestion++;

                if (currentTry >= MAX_TRIES)
                {
                    attempts.replace(currentTry, attempts.get(currentTry) + INCREMENT_AMOUNT);
                    System.out.println("Ran out of tries, next question.");
                }

                currentAttempt++;
                currentTry = START_INDEX;
            } else {
                System.out.println("Try again!");
            }
        }
    }

    /*
     * Prints the score.
     */
    private void printScore()
    {
        final StringBuilder sb;
        final Set<Integer> keyList;
        keyList = attempts.keySet();

        for (final Integer key : keyList) {
            if (key == CORRECT_ON_FIRST_ATTEMPT) {
                System.out.println(attempts.get(key) + " correct answers on the first attempt.");
            }

            if (key == CORRECT_ON_SECOND_ATTEMPT) {
                System.out.println(attempts.get(key) + " correct answers on the second attempt.");
            }

            if (key == INCORRECT_AFTER_TWO_ATTEMPTS) {
                System.out.println(attempts.get(key) + " incorrect answers on the two attempts each.");
            }

            handleUpdateScoreObject(key);
        }

        sb = new StringBuilder();
    }

    /*
     * Handles the parsing of the total scores.
     * @param totalScore as a String.
     */
    private void handleAddTotalScores(final String totalScore)
    {
        final String[] parts;
        parts = totalScore.split("\\D+");

        for (String part : parts) {
            if (!part.isEmpty()) {
                int number;
                number = Integer.parseInt(part);
                totalScores.add(number);
                break;
            }
        }
    }

    /*
     * Prints the quit message.
     */
    private void printQuitMessage()
    {
        int currentHighest;
        int highscoreIndex;
        highscoreIndex = START_INDEX;
        currentHighest = STARTING_TOTAL;

        // Parse the file to collect all the scores.
        if (Files.exists(inputFilePath)) {

            // Using try-with-resources (large file) - to be auto-closed, resource needs to be declared inside try block
            try (final BufferedReader br = Files.newBufferedReader(inputFilePath))
            {
                String currentLine;

                currentLine = br.readLine();

                // Check if the first and subsequent lines are not null.
                while (currentLine != null)
                {
                    if (currentLine.contains("Date and Time:"))
                    {
                        dateAndTimes.add(currentLine);
                    }
                    if (currentLine.contains("Total Score:"))
                    {
                        handleAddTotalScores(currentLine);
                    }

                    currentLine = br.readLine();
                }
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }

        for (int i = START_INDEX; i < totalScores.size(); i++) {
            if (totalScores.get(i) > currentHighest)
            {
                currentHighest = totalScores.get(i);
                highscoreIndex = i;
            }
        }

        if (score.getTotalScore() < currentHighest)
        {
            System.out.println("You did not beat the high score of " + currentHighest
                    + " points per game from " + formatDateTimeScore(dateAndTimes.get(highscoreIndex)));
        }
        else
        {
            System.out.println("CONGRATULATIONS! You are the new high score with an average of " + score.getTotalScore()
                    + " points per game from " + score.getFormattedDateTime());
        }
    }

    /*
     * Formats the string to remove the "Date and Time: " from the string.
     * @param dateAndTime
     * @return
     */
    private static String formatDateTimeScore(final String dateAndTime)
    {
        return dateAndTime.split("e: ")[RIGHT_SIDE];
    }

    /*
     * Properly formats the fact question.
     * @param randomFact as a String
     * @return the fact question, properly formatted
     */
    private static String handleFactQuestion(final String randomFact)
    {
        return "Which country is "
                + randomFact
                .substring(START_INDEX, END_INDEX_RANDOM_FACT_FIRST_LETTER)
                .toLowerCase()
                + randomFact
                .substring(END_INDEX_RANDOM_FACT_FIRST_LETTER);
    }

    /**
     * Handles the updating of the score object.
     * @param key as the key of the attempts hashmap.
     */
    private void handleUpdateScoreObject(final int key)
    {
        if (key == CORRECT_ON_FIRST_ATTEMPT) {
            score.setNumCorrectFirstAttempt(attempts.get(key));
        }

        if (key == CORRECT_ON_SECOND_ATTEMPT) {
            score.setNumCorrectSecondAttempt(attempts.get(key));
        }

        if (key == INCORRECT_AFTER_TWO_ATTEMPTS) {
            score.setNumIncorrectTwoAttempts(attempts.get(key));
        }
    }

    /*
     * Checks if the answer is correct.
     * @param currentQuestion
     * @param answer
     * @param typeOfQuestion
     * @return a boolean depending on the correctness of the answer.
     */
    private boolean handleAnswer(final int currentQuestion, final String answer, final int typeOfQuestion)
    {
        final String name;
        final String capital;

        name = questions[currentQuestion].getName();
        capital = questions[currentQuestion].getCapitalCityName();

        if ((typeOfQuestion == QUESTION_TYPE_RANDOM_FACT ||
            typeOfQuestion == QUESTION_TYPE_WHICH_CAPITAL) &&
            answer.equalsIgnoreCase(name)
        )
        {
            System.out.println("Correct!");
            return true;
        }

        if (typeOfQuestion == QUESTION_TYPE_WHICH_COUNTRY &&
            answer.equalsIgnoreCase(capital)
        )
        {
            System.out.println("Correct!");
            return true;
        }
        return false;
    }

    /**
     * Generates the ten questions to ask.
     * @return the questions as a List
     */
    private Country[] generateQuestions()
    {
        final List<Country> questions;
        questions = new ArrayList<>(countries.values());

        Collections.shuffle(questions);

        return questions.stream()
                .limit(MAX_QUESTION_SIZE)
                .toArray(Country[]::new);
    }
}
