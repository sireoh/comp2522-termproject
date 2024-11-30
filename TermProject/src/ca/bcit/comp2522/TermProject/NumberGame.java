package ca.bcit.comp2522.TermProject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * NumberGame is the Number game.
 * @author Vincent Fung
 * @version 2024
 */
public class NumberGame extends Application
{
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final Board board;

    {
        board = new Board();
    }

    /**
     * The main entry point of the application.
     * @param args unused.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * @param stage as a Stage.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle("Number Game");

        final VBox layout;
        final Scene scene;
        layout = board.generateLayout();

        scene = new Scene(layout, WIDTH, HEIGHT);

        board.setStage(stage);
        board.handleStyling(scene);

        stage.setScene(scene);

        // Show the stage and request focus explicitly
        stage.show();
        stage.toFront();    // Bring the window to the foreground
        stage.requestFocus(); // Request focus

        // Display the start alert
        board.showStartAlert();
    }



}
