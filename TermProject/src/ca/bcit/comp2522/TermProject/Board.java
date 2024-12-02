package ca.bcit.comp2522.TermProject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Board is a concrete Class that gets called when creating the
 * main VBox in NumberGame.
 * @author Vincent Fung
 * @version 2024
 */
public class Board extends NumberGameAbstractClass implements NumberGameInterface {

    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;
    private static final int MAX_COLUMN_COUNT = 5;
    private final static int MAX_ROW_COUNT = 4;
    private static final int BUTTON_SIZE = 50;
    private static final int SPACING = 5;

    public Board(final Stage stage)
    {
        super(stage);
    }

    @Override
    public VBox generateLayout() {
        final VBox root;
        final GridPane gridLayout;

        root = new VBox(SPACING);
        gridLayout = new GridPane();

        scoreLabel = new Label("Next number: " + currentNumber + " - Select a slot.");
        scoreLabel.setMaxWidth(SCENE_WIDTH);
        scoreLabel.setAlignment(Pos.CENTER);

        gridLayout.setHgap(SPACING);
        gridLayout.setVgap(SPACING);

        for (int i = STARTING_INDEX; i < MAX_BOARD_SIZE; i++)
        {
            final int index;
            index = i;
            buttonList[i] = new Button();
            buttonList[i].setMinSize(BUTTON_SIZE, BUTTON_SIZE);
            buttonList[i].getStyleClass().add("button");
            buttonList[i].setPrefSize((double) SCENE_WIDTH / MAX_COLUMN_COUNT, (double) SCENE_HEIGHT / MAX_ROW_COUNT);
            buttonList[i].setOnAction(event -> handlePlacement(index));
            gridLayout.add(buttonList[i], i % MAX_COLUMN_COUNT, i / MAX_COLUMN_COUNT);
        }

        root.getChildren().addAll(scoreLabel, gridLayout);
        return root;
    }

    /**
     * Handles the styling of the Board.
     * @param scene as a Scene object.
     */
    @Override
    public void handleStyling(final Scene scene)
    {
        final String css;
        css = String.valueOf(getClass().getResource("styles.css"));

        if (css != null)
        {
            scene.getStylesheets().add(css);
        }
    }

    /**
     * Shows the start alert.
     */
    @Override
    public void showStartAlert() {
        final Alert startAlert;

        startAlert = new Alert(Alert.AlertType.INFORMATION);
        startAlert.setTitle("Welcome");
        startAlert.setHeaderText("Welcome to the 20-Number Challenge! Click 'Play' to start.");

        startAlert.show();
        handleNewGame();
    }

    /*
     * Updates the GUI
     */
    @Override
    protected void updateGUI() {
        scoreLabel.setText("Next number: " + currentNumber + " - Select a slot.");
    }
}
