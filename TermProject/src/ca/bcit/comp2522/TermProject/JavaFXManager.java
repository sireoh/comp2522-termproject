package ca.bcit.comp2522.TermProject;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Java FX Manager, manages the running of Number Game.
 */
public class JavaFXManager {
    private static NumberGame numberGameInstance;
    private static boolean isJavaFXInitialized = false;

    public static void startNumberGame() {
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

    private static void startGameInstance() {
        if (numberGameInstance == null) {
            numberGameInstance = new NumberGame();
            numberGameInstance.start(new Stage());
        } else {
            numberGameInstance.resetGame();
        }
    }
}
