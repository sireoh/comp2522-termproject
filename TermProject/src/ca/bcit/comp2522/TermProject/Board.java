package ca.bcit.comp2522.TermProject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Board class represents the Board for the
 * Number Game
 * @author Vincent Fung
 * @version 2024
 */
public class Board
{
    private final static int PADDING = 5;
    private final static int VBOX_PADDING = 10;
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final static int MAX_COLUMN_COUNT = 5;
    private final static int MAX_ROW_COUNT = 4;
    private final static int STARTING_INDEX = 0;
    private final static int MIN_BUTTON_VALUE = 1;
    private final static int MAX_BUTTON_VALUE = 1000;
    private final static int MAX_BOARD_SIZE = 20;
    private final static int ERROR_CHECKING_SCORE_STARTING_INDEX = 1;
    private final static int PREVIOUS_CELL = 1;
    private final static int EMPTY_CELL = 0;
    private final static int NO_ERRORS = 0;
    private final Random random;
    private int currentNumber;
    private final int[] board;
    private Alert lossAlert;
    private Label label;
    private List<Button> buttons;

    public Board()
    {
        random = new Random();

        currentNumber = random.nextInt(MIN_BUTTON_VALUE, MAX_BUTTON_VALUE);
        board = new int[MAX_BOARD_SIZE];
    }

    /*
     * Generates the layout for the Number Game.
     * @return the layout as a VBox
     */
    public VBox generateLayout()
    {
        final VBox vbox;
        int index;
        final GridPane gridLayout;
        vbox = new VBox(VBOX_PADDING);
        gridLayout = new GridPane();

        gridLayout.setHgap(PADDING);
        gridLayout.setVgap(PADDING);
        index = STARTING_INDEX;
        label = new Label(formatNextNumberText(currentNumber));
        label.setMaxWidth(WIDTH);
        label.setAlignment(Pos.CENTER);
        buttons = new ArrayList<>();

        for (int row = STARTING_INDEX; row < MAX_ROW_COUNT; row++) {
            for (int col = STARTING_INDEX; col < MAX_COLUMN_COUNT; col++) {
                final Button b;
                final String s;
                final int tempInt;
                b = new Button("[]");

                b.setPrefSize((double) WIDTH / MAX_COLUMN_COUNT, (double) HEIGHT / MAX_ROW_COUNT);
                tempInt = index;

                b.setOnAction(click -> {
                    final Button temp;
                    temp = (Button) click.getSource();

                    handleInsertion(temp, tempInt);

                    if (countItems() < MAX_BOARD_SIZE) {
                        label.setText(formatNextNumberText(currentNumber));
                    } else {
                        calculateScore();
                    }
                });

                index++;
                gridLayout.add(b, col, row);
            }
        }

        vbox.getChildren().addAll(
                label,
                gridLayout
        );

        return vbox;
    }

    /*
     * Calculates the final score
     */
    private void calculateScore()
    {
        int errors;
        errors = STARTING_INDEX;

        for (int i = ERROR_CHECKING_SCORE_STARTING_INDEX; i < MAX_BOARD_SIZE; i++) {
            if (board[i] < board[i - PREVIOUS_CELL]) {
                errors++;
            }
        }

        if (errors > NO_ERRORS) {
            final Button tryAgainButton;
            lossAlert = new Alert(Alert.AlertType.WARNING);
            tryAgainButton = ((Button) lossAlert.getDialogPane().lookupButton(ButtonType.OK));

            tryAgainButton.setText("Try Again");
            tryAgainButton.setOnAction(event -> {
                handleNewGame();
            });
            label.setText("Click 'Try Again' to start a new game.");
            lossAlert.setTitle("Game Over");
            lossAlert.setHeaderText("Welcome to the 20-Number Challenge! Click 'Try Again' to start.");
            lossAlert.show();
        }
    }

    /*
     * Handles the creation of a new game,
     * and resets the board.
     */
    private void handleNewGame()
    {
        // Clears the board.
        for (int i = STARTING_INDEX; i < MAX_BOARD_SIZE; i++)
        {
            buttons.get(i).setText("[]");
            board[i] = EMPTY_CELL;
        }

        currentNumber = random.nextInt(MAX_BUTTON_VALUE);
        label.setText(formatNextNumberText(currentNumber));
    }

    /*
     * Counts the number of filled items in the array.
     * @return the number of filled items as an int.
     */
    private int countItems()
    {
        int count = STARTING_INDEX;

        for (final int number : board)
        {
            if (number != EMPTY_CELL)
            {
                count++;
            }
        }

        return count;
    }

    public void handleStyling(final Scene scene)
    {
        final URL styleSheetURL;
        styleSheetURL = getClass().getResource("./styles.css");

        if (styleSheetURL == null)
        {
            System.out.println("Stylesheet was not found");
        }
        else
        {
            scene.getStylesheets().add(styleSheetURL.toExternalForm());
        }
    }

    /*
     * Formats the nextNumber nicely.
     * @param currentNumber as an int.
     * @return the formatted String as a String.
     */
    private String formatNextNumberText(final int currentNumber)
    {
        return "Next number: " + currentNumber + " - Select a slot.";
    }

    /*
     * Handles the insertion of the buttons to the boardArray.
     * @param temp
     * @param index
     */
    private void handleInsertion(final Button temp, final int index)
    {
        if (board[index] == EMPTY_CELL) {
            // Store the button in an array to be reset later.
            buttons.add(temp);

            // Sets the number
            temp.setText(currentNumber + "");
            board[index] = currentNumber;

            // Randomize the global int for the next function.
            currentNumber = random.nextInt(MIN_BUTTON_VALUE, MAX_BUTTON_VALUE);
        } else {
            System.out.println("Box has already been filled.");
        }

        System.out.println(Arrays.toString(board));
    }
}
