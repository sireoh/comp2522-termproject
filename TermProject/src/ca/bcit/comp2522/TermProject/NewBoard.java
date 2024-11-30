package ca.bcit.comp2522.TermProject;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NewBoard {
    private final int rows = 4;
    private final int cols = 5;
    private final int[][] grid = new int[rows][cols];
    private List<Integer> randomNumbers;
    private int currentNumber = 0;
    private int placedCount = 0;
    private boolean gameOver = false;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int totalPlaced = 0;

    private final Label currentNumberLabel;
    private final Label scoreLabel;
    private final Button[][] buttons;
    private final VBox ui;

    public NewBoard() {
        currentNumberLabel = new Label("Current Number: ");
        scoreLabel = new Label("Score: ");
        buttons = new Button[rows][cols];
        ui = new VBox(10);

        GridPane gridPane = new GridPane();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Button button = new Button("-");
                button.setMinSize(50, 50);
                final int row = i, col = j;
                button.setOnAction(e -> placeNumber(row, col));
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        Button resetButton = new Button("Try Again");
        resetButton.setOnAction(e -> resetGame());

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> showFinalScore());

        ui.getChildren().addAll(currentNumberLabel, gridPane, scoreLabel, resetButton, quitButton);

        startGame();
    }

    public VBox getUI() {
        return ui;
    }

    private void startGame() {
        gamesPlayed++;
        generateNumbers();
        currentNumber = randomNumbers.remove(0);
        placedCount = 0;
        gameOver = false;
        clearGrid();
        updateUI();
    }

    private void resetGame() {
        startGame();
    }

    private void generateNumbers() {
        randomNumbers = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < rows * cols; i++) {
            randomNumbers.add(rand.nextInt(1000) + 1);
        }
        Collections.sort(randomNumbers); // Sort to simplify validation
    }

    private void clearGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private void placeNumber(int row, int col) {
        if (gameOver || grid[row][col] != 0) return;

        if (isValidPlacement(row, col)) {
            grid[row][col] = currentNumber;
            placedCount++;
            if (randomNumbers.isEmpty()) {
                gameOver = true;
                gamesWon++;
            } else {
                currentNumber = randomNumbers.remove(0);
            }
        } else {
            gameOver = true;
        }

        totalPlaced += placedCount;
        updateUI();
    }

    private boolean isValidPlacement(int row, int col) {
        int left = (col > 0) ? grid[row][col - 1] : Integer.MIN_VALUE;
        int right = (col < cols - 1) ? grid[row][col + 1] : Integer.MAX_VALUE;
        int above = (row > 0) ? grid[row - 1][col] : Integer.MIN_VALUE;
        int below = (row < rows - 1) ? grid[row + 1][col] : Integer.MAX_VALUE;

        return currentNumber > left && currentNumber < right && currentNumber > above && currentNumber < below;
    }

    private void updateUI() {
        currentNumberLabel.setText("Current Number: " + (gameOver ? "Game Over!" : currentNumber));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = grid[i][j];
                buttons[i][j].setText(value == 0 ? "-" : String.valueOf(value));
            }
        }
        scoreLabel.setText("Games Played: " + gamesPlayed + ", Games Won: " + gamesWon +
                ", Total Numbers Placed: " + totalPlaced + ", Avg: " + (gamesPlayed == 0 ? 0 : totalPlaced / gamesPlayed));
    }

    private void showFinalScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Final Score");
        alert.setHeaderText("Game Over");
        alert.setContentText("Games Played: " + gamesPlayed + "\nGames Won: " + gamesWon +
                "\nTotal Numbers Placed: " + totalPlaced + "\nAverage Numbers Placed per Game: " +
                (gamesPlayed == 0 ? 0 : totalPlaced / gamesPlayed));
        alert.showAndWait();
    }
}
