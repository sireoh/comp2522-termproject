package ca.bcit.comp2522.TermProject;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Java FX Manager, manages the running of Number Game.
 * This class implements the Singleton design pattern.
 * @author Vincent Fung
 * @version 2024
 */
public class JavaFXManager {
    private static JavaFXManager instance;
    private static NumberGame numberGameInstance;
    private static boolean isJavaFXInitialized = false;

    // Private constructor to prevent instantiation
    private JavaFXManager() {}

    /**
     * Returns the singleton instance of the JavaFXManager.
     * @return The singleton instance.
     */
    public static JavaFXManager getInstance() {
        if (instance == null) {
            synchronized (JavaFXManager.class) {
                if (instance == null) {
                    instance = new JavaFXManager();
                }
            }
        }
        return instance;
    }

    /**
     * Helper function that starts the NumberGame on
     * a new thread.
     */
    public void startNumberGame() {
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
            Platform.runLater(JavaFXManager.getInstance()::startGameInstance);
        }
    }

    /*
     * Helper function that aids in resetting the game.
     */
    private void startGameInstance() {
        if (numberGameInstance == null) {
            numberGameInstance = new NumberGame();
            numberGameInstance.start(new Stage());
        } else {
            numberGameInstance.resetGame();
        }
    }
}