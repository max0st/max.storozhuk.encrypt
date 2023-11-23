/**
 * The `GUI` class provides a simple JavaFX graphical user interface (GUI) for interacting with the file
 * encryption and decryption operations defined in the `FileService` class. It allows users to select a command,
 * enter file paths, keys, and perform actions through a graphical interface.
 * <p>
 * This class includes the following methods:
 * <p>
 * 1. `launchGUI()`: Launches the GUI application.
 * <p>
 * 2. `main(String[] args)`: The main method to launch the GUI application.
 * <p>
 * 3. `showErrorDialog(String message)`: Displays an error dialog with the specified message.
 * <p>
 * 4. `showSuccessDialog(String operation)`: Displays a success dialog for the specified operation.
 * <p>
 * 5. `showSuccessDialog(String operation, int key)`: Displays a success dialog for the specified operation with the
 * probable key.
 * <p>
 * 6. `start(Stage primaryStage)`: Initializes and sets up the JavaFX GUI components.
 */

package com.javarush.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
    private final GUIController controller = new GUIController();

    public static void main(String[] args) {
        launch(args);
    }

    public void launchGUI() {
        Application.launch(GUI.class);
    }

    @Override
    public void start(Stage primaryStage) {
        controller.initialize(primaryStage);
    }
}
