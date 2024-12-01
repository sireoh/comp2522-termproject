package ca.bcit.comp2522.TermProject;

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
import java.util.Random;

/**
 * Board class represents the Board for the Number Game.
 * @version 2024
 */
public class Board {

    private final static int MAX_ROW_COUNT = 4;
    private final static int MAX_COLUMN_COUNT = 5;
    private static final int BUTTON_SIZE = 50;
    private static final int SPACING = 5;
    private final static int MAX_BOARD_SIZE = 20;
    private static final int MAX_NUMBER_VALUE = 1000;
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;

    private final Random random;
    private final Button[] buttons;
    private final int[] board;
    private final List<Integer> usedNumbers;

    private int currentNumber;
    private int successfulPlacements;
    private Label scoreLabel;
    private Stage stage;
    private int totalGameOver;
    private int totalGamesPlayed;

    private final Alert finishAlert;
    private final Button tryAgainButton;
    private final Button quitButton;

    public Board() {
        finishAlert = new Alert(Alert.AlertType.CONFIRMATION);
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

        random = new Random();
        buttons = new Button[MAX_BOARD_SIZE];
        board = new int[MAX_BOARD_SIZE];
        usedNumbers = new ArrayList<>();
        resetGameStats();
    }

    /**
     * Generates the layout for the Number Game.
     *
     * @return the layout as a VBox.
     */
    public VBox generateLayout() {
        final VBox root;
        root = new VBox(SPACING);

        scoreLabel = new Label("Next number: " + currentNumber + " - Select a slot.");
        scoreLabel.getStyleClass().add("label");
        scoreLabel.setMaxWidth(WIDTH);
        scoreLabel.setAlignment(Pos.CENTER);

        final GridPane gridLayout;
        gridLayout = new GridPane();
        gridLayout.setHgap(SPACING);
        gridLayout.setVgap(SPACING);

        for (int i = 0; i < MAX_BOARD_SIZE; i++) {
            final int index = i;
            buttons[i] = new Button();
            buttons[i].setMinSize(BUTTON_SIZE, BUTTON_SIZE);
            buttons[i].getStyleClass().add("button");
            buttons[i].setPrefSize((double) WIDTH / MAX_COLUMN_COUNT, (double) HEIGHT / MAX_ROW_COUNT);
            buttons[i].setOnAction(event -> handlePlacement(index));
            gridLayout.add(buttons[i], i % MAX_COLUMN_COUNT, i / MAX_COLUMN_COUNT);
        }

        root.getChildren().addAll(scoreLabel, gridLayout);
        return root;
    }

    /**
     * Sets the stage for the game.
     *
     * @param stage as the stage to set.
     */
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    /**
     * Applies the styling to the game.
     *
     * @param scene the Scene to style.
     */
    public void handleStyling(final Scene scene) {
        final String css = String.valueOf(getClass().getResource("styles.css"));
        if (css != null) {
            scene.getStylesheets().add(css);
        }
    }

    /**
     * Shows the welcome alert.
     */
    public void showStartAlert() {
        final Alert startAlert;
        final Button playButton;

        startAlert = new Alert(Alert.AlertType.INFORMATION);
        startAlert.setTitle("Welcome");
        startAlert.setHeaderText("Welcome to the 20-Number Challenge! Click 'Play' to start.");
        playButton = ((Button) startAlert.getDialogPane().lookupButton(ButtonType.OK));
        playButton.setText("Play");

        startAlert.show();
        handleNewGame();
    }

    private void handleNewGame() {
        for (int i = 0; i < MAX_BOARD_SIZE; i++) {
            board[i] = 0;
            buttons[i].setText("");
            buttons[i].setDisable(false);
        }
        currentNumber = generateNextNumber();
        successfulPlacements = 0;
        updateScoreLabel();
        highlightAvailableSpots();
    }

    private void handlePlacement(int index) {
        board[index] = currentNumber;
        buttons[index].setText(String.valueOf(currentNumber));
        buttons[index].setDisable(true);

        successfulPlacements++;
        if (successfulPlacements == MAX_BOARD_SIZE) {
            finishGame(true);
        } else {
            currentNumber = generateNextNumber();
            highlightAvailableSpots();
            if (!canPlaceNextNumber()) {
                finishGame(false);
            }
        }
        updateScoreLabel();
    }

    private void finishGame(final boolean hasWon) {
        if (hasWon)
        {
            finishAlert.setTitle("You Won!");
        } else {
            finishAlert.setTitle("Game Over!");
            finishAlert.setHeaderText("Impossible to place the next number: " + currentNumber + ". Try again?");
        }
        finishAlert.show();
    }

    private boolean canPlaceNextNumber() {
        for (int i = 0; i < MAX_BOARD_SIZE; i++) {
            if (board[i] == 0 && isValidPlacement(i)) {
                return true;
            }
        }
        return false;
    }

    private void highlightAvailableSpots() {
        for (int i = 0; i < MAX_BOARD_SIZE; i++) {
            if (board[i] == 0 && isValidPlacement(i)) {
                buttons[i].setDisable(false);
            } else {
                buttons[i].setDisable(true);
            }
        }
    }

    private boolean isValidPlacement(int index) {
        for (int i = index - 1; i >= 0; i--) {
            if (board[i] != 0 && board[i] >= currentNumber) {
                return false;
            }
        }
        for (int i = index + 1; i < MAX_BOARD_SIZE; i++) {
            if (board[i] != 0 && board[i] <= currentNumber) {
                return false;
            }
        }
        return true;
    }

    private int generateNextNumber() {
        int number;
        do {
            number = random.nextInt(MAX_NUMBER_VALUE) + 1;
        } while (usedNumbers.contains(number));
        usedNumbers.add(number);
        return number;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Next number: " + currentNumber + " - Select a slot.");
    }

    private void resetGameStats() {
        successfulPlacements = 0;
        totalGamesPlayed = 0;
        totalGameOver = 0;
    }

    /**
     * Resets the game state to start a new game.
     */
    public void resetGame() {
        // Reset the board and buttons
        for (int i = 0; i < MAX_BOARD_SIZE; i++) {
            board[i] = 0;
            buttons[i].setText("");
            buttons[i].setDisable(false);
        }
        // Clear used numbers and generate the first number
        usedNumbers.clear();
        currentNumber = generateNextNumber();

        // Reset successful placements and update the score label
        successfulPlacements = 0;
        updateScoreLabel();

        // Highlight available spots on the board
        highlightAvailableSpots();
    }

}
