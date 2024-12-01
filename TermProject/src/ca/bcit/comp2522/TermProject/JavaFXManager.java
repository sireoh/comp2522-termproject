package ca.bcit.comp2522.TermProject;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Java FX Manager, manages the running of Number Game.
 * @author Vincent Fung
 * @version 2024
 */
public class JavaFXManager {
    private static NumberGame numberGameInstance;
    private static boolean isJavaFXInitialized = false;

    /**
     * Helper function that starts the NumberGame on
     * a new thread.
     */
    public static void startNumberGame() {
        Platform.setImplicitExit(false);

        if (!isJavaFXInitialized) {
            new Thread(() -> {
                try {
                    Platform.startup(() -> {
                        isJavaFXInitialized = true;
                        startGameInstance();
                    });
                } catch (IllegalStateException e) {
                    isJavaFXInitialized = true;
                    startGameInstance();
                }
            }).start();
        } else {
            Platform.runLater(JavaFXManager::startGameInstance);
        }
    }

    /*
     * Helper function that aids in resetting the game.
     */
    private static void startGameInstance() {
        if (numberGameInstance == null) {
            numberGameInstance = new NumberGame();
            numberGameInstance.start(new Stage());
        } else {
            numberGameInstance.resetGame();
        }
    }
}
