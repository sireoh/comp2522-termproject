package ca.bcit.comp2522.TermProject;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Java FX Manager, manages the running of Number Game
 * @author Vincent Fung
 * @version 2024
 */
public class JavaFXManager {

    /**
     * Static helper function that starts number game.
     */
    public static void startNumberGame() {
        Platform.runLater(() -> {
            try {

                new NumberGame().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}