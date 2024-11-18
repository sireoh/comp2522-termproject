package ca.bcit.comp2522.TermProject;

import javafx.application.Application;
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
    private final static Board board;

    static {
        board = new Board();
    }

    /**
     *
     * @param stage as a Stage.
     */
    @Override
    public void start(final Stage stage) throws IOException
    {
        stage.setTitle("Number Game");

        final VBox layout;
        final Scene scene;
        layout = board.generateLayout();

        scene = new Scene(layout, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application.
     * @param args unused.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
