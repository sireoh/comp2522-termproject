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
    private NewBoard board;

    {
        board = new NewBoard();
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

        board = new NewBoard();

        VBox root = new VBox(10);
        root.getChildren().addAll(board.getUI());

        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.show();
    }
}
