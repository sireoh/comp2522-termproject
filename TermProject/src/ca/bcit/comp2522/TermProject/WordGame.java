package ca.bcit.comp2522.TermProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

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
    private final static int OFFSET = 1;
    private final static int MAX_ATTEMPT_SIZE = 3;
    private final static int END_INDEX_RANDOM_FACT_FIRST_LETTER = 1;
    private final static int CORRECT_ON_FIRST_ATTEMPT = 1;
    private final static int CORRECT_ON_SECOND_ATTEMPT = 2;
    private final static int INCORRECT_AFTER_TWO_ATTEMPTS = 3;
    private final static int INCREMENT_AMOUNT = 1;
    private final static int RIGHT_SIDE = 1;
    private final Map<Integer, Integer> attempts;
    private boolean isPlaying;
    private final Scanner scanner;
    private static final String OUTPUT_FILE = "scoreFile/score.txt";
    private List<Score> totalScores;
    private int gamesPlayed;
    private static final int GAMES_PLAYED_STARTING_AMOUNT = 1;
    private final Random random;
    private int currentQuestionIndex;

    public WordGame()
    {
        final World world;
        final LocalDateTime currentTime;
        attempts = new HashMap<>();
        attempts.put(CORRECT_ON_FIRST_ATTEMPT, START_INDEX);
        attempts.put(CORRECT_ON_SECOND_ATTEMPT, START_INDEX);
        attempts.put(INCORRECT_AFTER_TWO_ATTEMPTS, START_INDEX);
        scanner = new Scanner(System.in);
        isPlaying = true;
        String choice;

        currentQuestionIndex = START_INDEX;
        random = new Random();
        gamesPlayed = GAMES_PLAYED_STARTING_AMOUNT;
        world = new World();
        countries = world.getCountries();
        questions = generateQuestions();
        totalScores = new ArrayList<>();

        setupScoreFile();

        while (isPlaying) {
            play();

            System.out.println("Thanks for playing !!");
            do {
                System.out.println("Play again? [y]es, [n]o");

                if (scanner.hasNext()) {
                    choice = scanner.nextLine();
                } else {
                    choice = "n";
                }

            } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));

            switch (choice.toLowerCase())
            {
                case "y" -> gamesPlayed++;
                case "n" -> {
                    printQuitMessage();
                    try {
                        final Score scoreToAdd;
                        scoreToAdd = new Score(LocalDateTime.now(),
                                gamesPlayed,
                                attempts.get(CORRECT_ON_FIRST_ATTEMPT),
                                attempts.get(CORRECT_ON_SECOND_ATTEMPT),
                                attempts.get(INCORRECT_AFTER_TWO_ATTEMPTS));
                        ScoreHandler.appendScoreToFile(scoreToAdd, OUTPUT_FILE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    isPlaying = false;
                }
                default -> System.out.println("Error.");
            }
        }
    }

    /*
     * Begins the WordGame.
     */
    private void play() {
        int currentAttempt = START_INDEX;
        int typeOfQuestion;

        while (currentQuestionIndex < MAX_QUESTION_SIZE) {
            System.out.println("Current Attempt: " + (currentAttempt + OFFSET));

            typeOfQuestion = random.nextInt(QUESTION_TYPE_SIZE);

            while (currentAttempt < MAX_ATTEMPT_SIZE) {
                handleAskingQuestion(typeOfQuestion);

                if (checkIfQuestionIsCorrect(typeOfQuestion)) {
                    System.out.println("Correct!");

                    if (currentAttempt == (CORRECT_ON_FIRST_ATTEMPT - OFFSET)) {
                        attempts.put(CORRECT_ON_FIRST_ATTEMPT,
                                attempts.get(CORRECT_ON_FIRST_ATTEMPT) + INCREMENT_AMOUNT);
                    } else if (currentAttempt == (CORRECT_ON_SECOND_ATTEMPT - OFFSET)) {
                        attempts.put(CORRECT_ON_SECOND_ATTEMPT,
                                attempts.get(CORRECT_ON_SECOND_ATTEMPT) + INCREMENT_AMOUNT);
                    }

                    currentQuestionIndex++;
                    currentAttempt = START_INDEX;
                    printScore();
                    break;
                } else {
                    System.out.println("Try Again.");
                    currentAttempt++;
                }

                if (currentAttempt == INCORRECT_AFTER_TWO_ATTEMPTS - OFFSET) {
                    System.out.println("The correct answer was: " +
                            questions[currentQuestionIndex].getName() +
                            ", " + questions[currentQuestionIndex].getCapitalCityName());
                    System.out.println("Ran out of tries, next question.");

                    attempts.put(INCORRECT_AFTER_TWO_ATTEMPTS,
                            attempts.get(INCORRECT_AFTER_TWO_ATTEMPTS) + INCREMENT_AMOUNT);
                    currentAttempt = START_INDEX;
                    currentQuestionIndex++;
                    printScore();
                    break;
                }
            }
        }
    }



    /*
     * Checks if the answer is correct.
     * @param typeOfQuestion
     * @return a boolean depending on the correctness of the answer.
     */
    private boolean checkIfQuestionIsCorrect(final int typeOfQuestion)
    {
        final String name;
        final String capital;
        final String userResponse;

        userResponse = scanner.nextLine();

        name = questions[currentQuestionIndex].getName();
        capital = questions[currentQuestionIndex].getCapitalCityName();

        if ((typeOfQuestion == QUESTION_TYPE_RANDOM_FACT ||
                typeOfQuestion == QUESTION_TYPE_WHICH_CAPITAL) &&
                userResponse.equalsIgnoreCase(name)
        )
        {
            return true;
        }

        if (typeOfQuestion == QUESTION_TYPE_WHICH_COUNTRY &&
                userResponse.equalsIgnoreCase(capital)
        )
        {
            return true;
        }
        return false;
    }

    /*
     * Asks the user the question.
     * @param currentQuestion as the current question in the array to ask.
     * @return the question as a String.
     */
    private void handleAskingQuestion(final int typeOfQuestion)
    {
        final String randomFact;
        final String questionText;
        randomFact = questions[currentQuestionIndex].getRandomFact();

        questionText = switch (typeOfQuestion) {
            case QUESTION_TYPE_RANDOM_FACT -> handleFactQuestion(randomFact);
            case QUESTION_TYPE_WHICH_CAPITAL -> questions[currentQuestionIndex].getCapitalCityName() +
                    " is the capital of which country?";
            case QUESTION_TYPE_WHICH_COUNTRY -> "What is the capital of " +
                    questions[currentQuestionIndex].getName() + "?";
            default -> "No function called";
        };

        System.out.println(questionText);
    }

    /*
     * Prints the score.
     */
    private void printScore()
    {
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
        }
    }

    /*
     * Prints the quit message.
     */
    private void printQuitMessage()
    {
        Score currentHighest;
        currentHighest = new Score(LocalDateTime.now(),
                            START_INDEX,
                            START_INDEX,
                            START_INDEX,
                            START_INDEX);

        try
        {
            totalScores = ScoreHandler.readScoresFromFile(OUTPUT_FILE);
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        for (final Score score : totalScores) {
            if (score.getScore() > currentHighest.getScore()) {
                currentHighest = score;
            }
        }

        final Score temp;
        temp = new Score(LocalDateTime.now(),
                gamesPlayed,
                attempts.get(CORRECT_ON_FIRST_ATTEMPT),
                attempts.get(CORRECT_ON_SECOND_ATTEMPT),
                attempts.get(INCORRECT_AFTER_TWO_ATTEMPTS));

        if ((temp.getScore() < currentHighest.getScore()))
        {
            System.out.println("You did not beat the high score of " + currentHighest.getScore()
                    + " points per game from " + currentHighest.getFormattedDateTime());
        }
        else
        {
            System.out.println("CONGRATULATIONS! You are the new high score with an average of " + temp.getScore()
                    + " points per game from " + temp.getFormattedDateTime());
        }
    }

    /*
     * Formats the string to remove the "Date and Time: " from the string.
     * @param dateAndTime
     * @return
     */
    private static String formatDateTimeScore(final String dateAndTime)
    {
        return dateAndTime.split(":")[RIGHT_SIDE].trim();
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

    /*
     * Helper function, helps set up the score file
     * if it doesn't exist already.
     */
    private void setupScoreFile()
    {
        final Path inputFilePath;
        final Path scoreDirectory;
        inputFilePath = Paths.get(OUTPUT_FILE);

        try {
            scoreDirectory = Paths.get("scoreFile");
            Files.createDirectories(scoreDirectory);

            scoreDirectory.resolve("score.txt");

            if (!Files.exists(inputFilePath)) {
                Files.createFile(inputFilePath);
            }
        } catch (final IOException e) {
            System.err.println("Error setting up score file: " + e.getMessage());
            throw new RuntimeException("Could not initialize score file", e);
        }
    }
}
