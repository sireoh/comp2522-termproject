import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class NewBoard
{
    private static final int    INITIAL_VALUE           = 0;
    private static final int    PLURAL_CHECK            = 1;
    private static final int    RANDOM_ADJUST           = 1;
    private static final int    BUTTON_LINE_BREAK       = 5;
    private static final int    SPACING                 = 10;
    private static final int    NUM_OF_BUTTONS          = 20;
    private static final int    BUTTON_SIZE             = 50;
    private static final int    SCENE_SIZE              = 400;
    private static final int    MAX_NUMBER_VALUE        = 1000;
    private static final String SCORE_LABEL             = "score-label";
    private static final String GAME_BUTTON             = "game-button";
    private static final String NUMBER_GAME_TITLE       = "Number Game";
    private static final String CSS_FILE_NAME           = "styles.css";
    private static final String GAME_LABEL              = "Place numbers in ascending order.";
    private static final String WON_TXT                 = "You Won! Try again?";
    private static final String GAME_OVER_TXT           = "Game Over";
    private static final String FINAL_SCORE_TXT         = "Final Score";
    private static final String WON_TITLE               = "Game won";
    private static final String TRY_AGAIN_BUTTON        = "Try Again";
    private static final String QUIT_BUTTON             = "Quit";
    private static final String	WORD_WON                = "won";
    private static final String	WORD_LOST               = "lost";
    private static final String	WORD_GAME               = "game";
    private static final String	WORD_GAMES              = "games";
    private static final String	WORD_PLACEMENT          = "placement";
    private static final String	WORD_PLACEMENTS         = "placements";
    private static final String SCORE_STAT_LABEL        = "Next number: %d | Placements: %d/%d";
    private static final String FINAL_SCORE_ALERT       = """
                                                            You %s %d out of %d %s,
                                                            with %d successful %s,
                                                            an average of %.2f per game""";
    private static final String GAME_OVER_ALERT         = """
                                                            Game Over!
                                                            Impossible to place the next number: %d.
                                                            
                                                            Successful placements in this game: %d.
                                                            Try again?""";

    private static Stage        primaryStage;
    private final Button[]      buttons;
    private final Label         scoreLabel;
    private final Random        random;
    private final List<Integer> usedNumbers;
    private int[]               board;
    private int                 totalGamesPlayed;
    private int                 totalGameOver;
    private int                 totalSuccessfulPlacements;
    private int                 successfulPlacementsInCurrentGame;
    private int                 nextNumber;

    public NumberGame()
    {
        buttons                             = new Button[NUM_OF_BUTTONS];
        scoreLabel                          = new Label(GAME_LABEL);
        random                              = new Random();
        usedNumbers                         = new ArrayList<>();
        totalGamesPlayed                    = INITIAL_VALUE;
        totalSuccessfulPlacements           = INITIAL_VALUE;
        successfulPlacementsInCurrentGame   = INITIAL_VALUE;
    }

    @Override
    public void play()
    {
        final VBox      root;
        final GridPane  gridPane;
        final Scene     scene;

        root = new VBox();
        root.setSpacing(SPACING);
        root.setAlignment(Pos.CENTER);

        scoreLabel.getStyleClass().add(SCORE_LABEL);
        gridPane = new GridPane();
        gridPane.setHgap(SPACING);
        gridPane.setVgap(SPACING);
        gridPane.setAlignment(Pos.CENTER);

        for(int i = INITIAL_VALUE; i < NUM_OF_BUTTONS; i++)
        {
            final Button    button;
            final int       index;

            button = new Button();
            button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
            button.getStyleClass().add(GAME_BUTTON);

            index = i;
            button.setOnAction(event -> placeNumber(index));
            buttons[i] = button;
            gridPane.add(button, i % BUTTON_LINE_BREAK, i / BUTTON_LINE_BREAK);
        }

        root.getChildren().addAll(scoreLabel, gridPane);
        scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_FILE_NAME)).toExternalForm());
        primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle(NUMBER_GAME_TITLE);
        startNewGame();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.showAndWait();
    }

    @Override
    public boolean canPlaceNextNumber()
    {
        for(int i = INITIAL_VALUE; i < NUM_OF_BUTTONS; i++)
        {
            if(board[i] == INITIAL_VALUE && !buttons[i].isDisable())
            {
                return true;
            }
        }
        return false;
    }

    private void displayFinalScore(final boolean finalScore)
    {
        final double    average;
        final String    resultMessage;
        final Alert     alert;

        average = totalGamesPlayed > INITIAL_VALUE ?
                (double) totalSuccessfulPlacements / totalGamesPlayed : INITIAL_VALUE;

        resultMessage = String.format(FINAL_SCORE_ALERT,
                finalScore ? WORD_WON : WORD_LOST,
                finalScore ? totalGamesPlayed - totalGameOver : totalGameOver,
                totalGamesPlayed,
                totalGamesPlayed == PLURAL_CHECK ? WORD_GAME : WORD_GAMES,
                successfulPlacementsInCurrentGame,
                successfulPlacementsInCurrentGame == PLURAL_CHECK ? WORD_PLACEMENT : WORD_PLACEMENTS,
                average);

        alert = new Alert(Alert.AlertType.INFORMATION, resultMessage, ButtonType.OK);
        alert.setTitle(FINAL_SCORE_TXT);
        alert.showAndWait();
    }

    private void finishGame(final String message)
    {
        totalGamesPlayed++;

        final Alert         alert;
        final ButtonType    tryAgain;
        final ButtonType    quit;

        alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        if(message.equals(WON_TXT))
        {
            alert.setTitle(WON_TITLE);
        }
        else
        {
            alert.setTitle(GAME_OVER_TXT);
        }

        tryAgain    = new ButtonType(TRY_AGAIN_BUTTON);
        quit        = new ButtonType(QUIT_BUTTON);
        alert.getButtonTypes().setAll(tryAgain, quit);

        primaryStage.setAlwaysOnTop(false);
        alert.showAndWait().ifPresent(response ->
        {
            displayFinalScore(message.equals(WON_TXT));

            if(response == tryAgain)
            {
                startNewGame();
            }
            else
            {
                final Stage stage;
                stage = (Stage) scoreLabel.getScene().getWindow();
                stage.close();
            }
        });
    }

    @Override
    public void highlightAvailableSpots()
    {
        for(int i = INITIAL_VALUE; i < NUM_OF_BUTTONS; i++)
        {
            buttons[i].setDisable(board[i] != INITIAL_VALUE);
        }

        for(int i = INITIAL_VALUE; i < NUM_OF_BUTTONS; i++)
        {
            if(board[i] == INITIAL_VALUE)
            {
                boolean valid = true;

                for(int j = i - RANDOM_ADJUST; j >= INITIAL_VALUE; j--)
                {
                    if(board[j] != INITIAL_VALUE && board[j] >= nextNumber)
                    {
                        valid = false;
                        break;
                    }
                }

                for(int j = i + RANDOM_ADJUST; j < NUM_OF_BUTTONS; j++)
                {
                    if(board[j] != INITIAL_VALUE && board[j] <= nextNumber)
                    {
                        valid = false;
                        break;
                    }
                }

                if(!valid)
                {
                    buttons[i].setDisable(true);
                }
            }
        }
    }

    private void placeNumber(final int index)
    {
        board[index] = nextNumber;
        buttons[index].setText(String.valueOf(nextNumber));
        buttons[index].setDisable(true);

        successfulPlacementsInCurrentGame++;
        totalSuccessfulPlacements++;

        if(successfulPlacementsInCurrentGame == NUM_OF_BUTTONS)
        {
            finishGame(WON_TXT);
        }
        else
        {
            nextNumber = random.nextInt(MAX_NUMBER_VALUE) + RANDOM_ADJUST;
            highlightAvailableSpots();

            if(!canPlaceNextNumber())
            {
                totalGameOver++;
                final String gameLostTxt;
                gameLostTxt = String.format(
                        GAME_OVER_ALERT,
                        nextNumber,
                        successfulPlacementsInCurrentGame
                );

                finishGame(gameLostTxt);
            }
        }

        updateScoreLabel();
    }

    private void startNewGame()
    {
        int   newNumber;
        board                               = new int[NUM_OF_BUTTONS];
        successfulPlacementsInCurrentGame   = INITIAL_VALUE;

        for(final Button button : buttons)
        {
            button.setText("");
            button.setDisable(false);
        }

        do
        {
            newNumber = random.nextInt(MAX_NUMBER_VALUE) + RANDOM_ADJUST;
        }
        while(usedNumbers.contains(newNumber));

        usedNumbers.add(newNumber);
        nextNumber = newNumber;

        updateScoreLabel();
        highlightAvailableSpots();
    }

    private void updateScoreLabel()
    {
        scoreLabel.setText(String.format(SCORE_STAT_LABEL,
                nextNumber, successfulPlacementsInCurrentGame, NUM_OF_BUTTONS));
    }
}
