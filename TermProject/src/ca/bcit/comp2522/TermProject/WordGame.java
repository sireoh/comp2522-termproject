package ca.bcit.comp2522.TermProject;

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
    private final static int MAX_TRIES = 3;
    private final static int END_INDEX_RANDOM_FACT_FIRST_LETTER = 1;
    private final static int CORRECT_ON_FIRST_ATTEMPT = 1;
    private final static int CORRECT_ON_SECOND_ATTEMPT = 2;
    private final static int INCORRECT_AFTER_TWO_ATTEMPTS = 3;
    private final static int INCREMENT_AMOUNT = 1;
    private final Map<Integer, Integer> attempts;
    private final Score score;
    private boolean isPlaying;
    final Scanner scanner;

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

        world = new World();
        countries = world.getCountries();
        questions = generateQuestions();
        score = new Score();

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

        /*
        3 correct answers on the first attempt
        - 2 correct answers on the second attempt
        - 5 incorrect answers on two attempts each
        */
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
