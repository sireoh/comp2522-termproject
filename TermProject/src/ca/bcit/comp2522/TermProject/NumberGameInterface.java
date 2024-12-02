package ca.bcit.comp2522.TermProject;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * Interface setups the NumberGame object function
 * configs.
 * @author Vincent Fung
 * @version 2024
 */
public interface NumberGameInterface
{
    /**
     * Generates a VBox layout.
     * @return layout as Vbox
     */
    VBox generateLayout();

    /**
     * Handles the styling of the CSS
     * @param scene as a Scene object.
     */
    void handleStyling(final Scene scene);

    /**
     * Shows the start alert.
     */
    void showStartAlert();
}
