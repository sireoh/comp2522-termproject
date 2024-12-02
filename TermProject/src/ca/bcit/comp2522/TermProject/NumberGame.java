package ca.bcit.comp2522.TermProject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * NumberGame is the Number game.
 * @author Vincent Fung
 * @version 2024
 */
public class NumberGame extends Application
{

    private static final String NUMBER_GAME_TITLE = "Number Game";
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    private Board board;

    /**
     * The main entry point of the application.
     * @param args unused.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * Starts the Number Game.
     */
    public void start(final Stage stage)
    {
        board = new Board(stage);

        VBox root = board.generateLayout();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        board.handleStyling(scene);

        stage.setTitle(NUMBER_GAME_TITLE);
        stage.setScene(scene);
        stage.show();

        board.showStartAlert();
    }

    /**
     * Resets the game to start a new round.
     */
    public void resetGame() {
        board.handleNewGame();
    }

}