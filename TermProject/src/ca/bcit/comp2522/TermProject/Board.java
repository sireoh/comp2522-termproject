package ca.bcit.comp2522.TermProject;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
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
    private int[] board;

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
        final Label label;
        final GridPane gridLayout;
        vbox = new VBox(VBOX_PADDING);
        gridLayout = new GridPane();

        gridLayout.setHgap(PADDING);
        gridLayout.setVgap(PADDING);
        int index = STARTING_INDEX;
        label = new Label("Please choose a location to place: " + currentNumber);

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
                        label.setText("Please choose a location to place: " + currentNumber);
                    } else {
                        calculateScore();
                        System.out.println("the board is filled homie");
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
     *
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
            System.out.println("ur so bad.");
        }
    }

    /*
     *
     * @return
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

    /*
     *
     * @param temp
     * @param index
     */
    private void handleInsertion(final Button temp, final int index)
    {
        if (board[index] == EMPTY_CELL) {
            temp.setText(currentNumber + "");
            board[index] = currentNumber;
            currentNumber = random.nextInt(MIN_BUTTON_VALUE, MAX_BUTTON_VALUE);
        } else {
            System.out.println("could not set. can u pick another box pls");
        }

        System.out.println(Arrays.toString(board));
    }
}
