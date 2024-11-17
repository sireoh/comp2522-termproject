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

    public WordGame()
    {
        final World world;

        world = new World();
        countries = world.getCountries();
        questions = generateQuestions();

        play();
    }

    /*
     * Begins the WordGame.
     */
    private void play()
    {
        final Random random;
        final Scanner scan;
        final String userInput;

        int currentQuestion = START_INDEX;
        String answer;
        boolean isCorrect;
        isCorrect = false;


        random = new Random();
        scan = new Scanner(System.in);
        int typeOfQuestion;
        typeOfQuestion = random.nextInt(QUESTION_TYPE_SIZE);

        while (currentQuestion < MAX_QUESTION_SIZE) {
            System.out.println(
                    switch (typeOfQuestion) { // Ensure random number is between 0 and 2
                        case QUESTION_TYPE_RANDOM_FACT -> "Which country is: " + questions[currentQuestion].getRandomFact();
                        case QUESTION_TYPE_WHICH_CAPITAL -> questions[currentQuestion].getCapitalCityName() + " is the capital of which country?";
                        case QUESTION_TYPE_WHICH_COUNTRY -> "What is the capital of: " + questions[currentQuestion].getName() + "?";
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
            if(isCorrect) {
                typeOfQuestion = random.nextInt(QUESTION_TYPE_SIZE);
                currentQuestion++;
            }
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

        System.out.println("Try again!");
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
