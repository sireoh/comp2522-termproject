package ca.bcit.comp2522.TermProject;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * NumberGameAbstractClass is an abstract class
 * that stores all the main helper functions for Board
 * @author Vincent Fung
 * @version 2024
 */
public abstract class NumberGameAbstractClass {

    protected final static int MAX_BOARD_SIZE = 20;
    protected static final int MAX_NUMBER_VALUE = 1000;
    protected final static int STARTING_INDEX = 0;
    protected final static int SCORE_RESET_NUM = 0;
    private final static int OFFSET = 1;

    protected final Random random;
    protected final int[] board;
    protected final List<Integer> numberHistory;
    protected int currentNumber;
    protected int successfulPlacements;

    protected final Button[] buttonList;
    protected final Alert finishAlert;
    protected Label scoreLabel;

    private final Button tryAgainButton;
    private final Button quitButton;

    /**
     * NumberGameAbstractClass, stores all the main logic of the game
     * and all its functions.
     */
    public NumberGameAbstractClass(final Stage stage) {
        random = new Random();
        board = new int[MAX_BOARD_SIZE];
        numberHistory = new ArrayList<>();
        buttonList = new Button[MAX_BOARD_SIZE];

        finishAlert = new Alert(Alert.AlertType.CONFIRMATION);
        finishAlert.setTitle("Game Over!");
        finishAlert.setHeaderText("Do you want to play again?");
        finishAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        tryAgainButton = ((Button) finishAlert.getDialogPane().lookupButton(ButtonType.OK));
        quitButton = ((Button) finishAlert.getDialogPane().lookupButton(ButtonType.CANCEL));

        tryAgainButton.setText("Try Again");
        quitButton.setText("Quit");

        tryAgainButton.setOnAction(event -> {
            handleNewGame();
        });

        quitButton.setOnAction(event -> {
            stage.close();
        });

        resetGameStats();

        resetGameStats();
    }

    /*
     * Helper function aids in handling a new game.
     */
    protected void handleNewGame() {
        for (int i = STARTING_INDEX; i < MAX_BOARD_SIZE; i++) {
            board[i] = SCORE_RESET_NUM;
            buttonList[i].setText("[]");
            buttonList[i].setDisable(false);
        }
        numberHistory.clear();
        currentNumber = generateNextNumber();
        successfulPlacements = SCORE_RESET_NUM;
        updateGUI();
        disableButtons();
    }

    /*
     * Helper function, aids in handling the placement.
     * @param index as an int
     */
    protected void handlePlacement(final int index) {
        board[index] = currentNumber;
        buttonList[index].setText(String.valueOf(currentNumber));
        buttonList[index].setDisable(true);
        successfulPlacements++;

        if (successfulPlacements == MAX_BOARD_SIZE) {
            finishGame(true);
        } else {
            currentNumber = generateNextNumber();
            disableButtons();
            if (!canPlaceNextNumber()) {
                finishGame(false);
            }
        }
        updateGUI();
    }

    /*
     * Helper function aids in setting up the final game alert.
     * @param hasWon as a boolean
     */
    protected void finishGame(final boolean hasWon) {
        finishAlert.setTitle(hasWon ? "You Won!" : "Game Over!");
        finishAlert.setHeaderText(hasWon ? null : "Impossible to place the next number: " + currentNumber + ". Try again?");

        finishAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            } else if (response == ButtonType.CANCEL) {
                handleNewGame();
            }
        });
    }


    /*
     * Helper function aids in checking if the user can place a number or not.
     * @return boolean to determine if the user can place a number
     */
    protected boolean canPlaceNextNumber() {
        for (int i = STARTING_INDEX; i < MAX_BOARD_SIZE; i++) {
            if (board[i] == SCORE_RESET_NUM && isValidPlacement(i)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Helper function that aids in disabling the buttons,
     * for the GUI.
     */
    protected void disableButtons() {
        for (int i = STARTING_INDEX; i < MAX_BOARD_SIZE; i++) {
            buttonList[i].setDisable(!(board[i] == SCORE_RESET_NUM && isValidPlacement(i)));
        }
    }

    /*
     * Helper function aids in checking if the current placement,
     * is valid.
     * @param index as an int.
     * @return boolean to check if the placement is valid.
     */
    protected boolean isValidPlacement(final int index) {
        for (int i = index - OFFSET; i >= STARTING_INDEX; i--) {
            if (board[i] != SCORE_RESET_NUM && board[i] >= currentNumber) {
                return false;
            }
        }
        for (int i = index + OFFSET; i < MAX_BOARD_SIZE; i++) {
            if (board[i] != SCORE_RESET_NUM && board[i] <= currentNumber) {
                return false;
            }
        }
        return true;
    }

    /*
     * Helper function that generates the next random number.
     * @return the next number as an int.
     */
    protected int generateNextNumber() {
        int number;
        do {
            number = random.nextInt(MAX_NUMBER_VALUE) + OFFSET;
        } while (numberHistory.contains(number));
        numberHistory.add(number);
        return number;
    }

    /*
     * Resets the game stats.
     */
    protected void resetGameStats() {
        successfulPlacements = SCORE_RESET_NUM;
    }

    /*
     * Updates the GUI
     */
    protected abstract void updateGUI();
}
