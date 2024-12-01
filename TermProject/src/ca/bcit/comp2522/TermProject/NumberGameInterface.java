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
    VBox generateLayout();
    void handleStyling(final Scene scene);
    void showStartAlert();
}
