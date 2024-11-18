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
    private final Random random;
    private String currentNumber;
    private String[] board;

    public Board()
    {
        random = new Random();

        currentNumber = random.nextInt(MIN_BUTTON_VALUE, MAX_BUTTON_VALUE) + "";
        board = new String[MAX_BOARD_SIZE];
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
        label = new Label(currentNumber);

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
                        label.setText(currentNumber);
                    } else {
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

    private int countItems()
    {
        int count = STARTING_INDEX;

        for (final String string : board)
        {
            if (string != null)
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
        if (board[index] == null) {
            temp.setText(currentNumber);
            board[index] = currentNumber;
            currentNumber = random.nextInt(MIN_BUTTON_VALUE, MAX_BUTTON_VALUE) + "";
        } else {
            System.out.println("could not set. can u pick another box pls");
        }

        System.out.println(Arrays.toString(board));
    }
}
