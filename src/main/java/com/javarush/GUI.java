/**
 * The `GUI` class provides a simple JavaFX graphical user interface (GUI) for interacting with the file
 * encryption and decryption operations defined in the `FileService` class. It allows users to select a command,
 * enter file paths, keys, and perform actions through a graphical interface.
 *
 * This class includes the following methods:
 *
 * 1. `launchGUI()`: Launches the GUI application.
 *
 * 2. `main(String[] args)`: The main method to launch the GUI application.
 *
 * 3. `showErrorDialog(String message)`: Displays an error dialog with the specified message.
 *
 * 4. `showSuccessDialog(String operation)`: Displays a success dialog for the specified operation.
 *
 * 5. `showSuccessDialog(String operation, int key)`: Displays a success dialog for the specified operation with the
 *    probable key.
 *
 * 6. `start(Stage primaryStage)`: Initializes and sets up the JavaFX GUI components.
 *
 *
 */

package com.javarush;

import com.javarush.fileoperations.FileService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {
    protected static void launchGUI() {
        Application.launch(GUI.class);
    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showSuccessDialog(String operation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(operation + " completed successfully!");
        alert.showAndWait();
    }

    private void showSuccessDialog(String operation, int key) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(operation + " completed successfully! Probable key is" + key);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Encryption/Decryption");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label commandLabel = new Label("Command:");
        ComboBox<String> commandComboBox = new ComboBox<>();
        commandComboBox.getItems().addAll("ENCRYPT", "DECRYPT", "BRUTE_FORCE");

        Label filePathLabel = new Label("File Path:");
        TextField filePathField = new TextField();
        filePathLabel.setVisible(false);
        filePathField.setVisible(false);

        Label keyLabel = new Label("Key:");
        TextField keyField = new TextField();
        keyLabel.setVisible(false);
        keyField.setVisible(false);

        Label filePathForStaticAnalysisLabel = new Label("File for Static Analysis:");
        TextField filePathForStaticAnalysisField = new TextField();
        filePathForStaticAnalysisLabel.setVisible(false);
        filePathForStaticAnalysisField.setVisible(false);

        Label infoLabel = new Label("If the field is empty,\n the default letter frequencies will be used");
        infoLabel.setVisible(false);

        Label infoForCommand = new Label("Choose the command to perform actions");

        Label infoKey = new Label("Provide the key:");
        infoKey.setVisible(false);

        Label infoForPath = new Label("Browse the path to the file.txt that you want to work with:");
        infoForPath.setVisible(false);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Button browseFilePathButton = new Button("Browse");
        browseFilePathButton.setOnAction(e -> filePathField.setText(fileChooser.showOpenDialog(primaryStage).getAbsolutePath()));

        browseFilePathButton.setVisible(false);

        Button browseFilePathForStatAnalysisButton = new Button("Browse");
        browseFilePathForStatAnalysisButton.setOnAction(e -> filePathForStaticAnalysisField.setText(fileChooser.showOpenDialog(primaryStage).getAbsolutePath()));

        browseFilePathForStatAnalysisButton.setVisible((false));


        Button executeButton = new Button("Execute");
        executeButton.setOnAction(e -> {
            String command = commandComboBox.getValue();
            String filePath = filePathField.getText();
            String key = keyField.getText();
            String filePathForStaticAnalysis = filePathForStaticAnalysisField.getText();

            if (command == null || command.isEmpty()) {
                showErrorDialog("Please select a command.");
                return;
            }

            if (filePath.isEmpty()) {
                showErrorDialog("Please enter a file path.");
                return;
            }

            if (!(command.equalsIgnoreCase("BRUTE_FORCE")) && (key.isEmpty() || !FileService.isNumeric(key))) {
                showErrorDialog("Please enter a key.");
                return;
            }

            if (!FileService.fileExists(filePath)) {
                showErrorDialog("File not found at the specified path.");
                return;
            }

            if ((command.equalsIgnoreCase("BRUTE_FORCE")) && !FileService.fileExists(filePathForStaticAnalysis) && !filePathForStaticAnalysis.isEmpty()) {
                showErrorDialog("File for static analysis not found at the specified path.");
                return;
            }

            switch (command) {
                case "ENCRYPT":
                    FileService.encryptFile(filePath, Integer.parseInt(key));
                    showSuccessDialog(command);
                    break;
                case "DECRYPT":
                    FileService.decryptFile(filePath, Integer.parseInt(key));
                    showSuccessDialog(command);
                    break;
                case "BRUTE_FORCE":
                    int decryptedKey;
                    if (!filePathForStaticAnalysis.isEmpty()) {
                        decryptedKey = FileService.decryptAnalysisBF(filePath, filePathForStaticAnalysis);
                    } else {
                        decryptedKey = FileService.decryptBF(filePath);
                    }
                    showSuccessDialog(command, decryptedKey);

                    break;
                default:
                    showErrorDialog("Invalid command.");
            }


        });


        executeButton.setVisible(false);

        commandComboBox.setOnAction(e -> {
            boolean isBruteForce = "BRUTE_FORCE".equals(commandComboBox.getValue());
            filePathLabel.setVisible(true);
            filePathField.setVisible(true);
            keyLabel.setVisible(!isBruteForce);
            keyField.setVisible(!isBruteForce);
            infoKey.setVisible(!isBruteForce);
            filePathForStaticAnalysisLabel.setVisible(isBruteForce);
            filePathForStaticAnalysisField.setVisible(isBruteForce);
            infoLabel.setVisible(isBruteForce);
            executeButton.setVisible(true);
            browseFilePathButton.setVisible(true);
            browseFilePathForStatAnalysisButton.setVisible(isBruteForce);
            infoForCommand.setVisible(false);
            infoForPath.setVisible(true);

            // Remove unnecessary nodes before adding new ones
            grid.getChildren().removeAll(keyLabel, keyField, infoKey, filePathForStaticAnalysisLabel, filePathForStaticAnalysisField, browseFilePathForStatAnalysisButton);

            if (isBruteForce) {
                grid.add(filePathForStaticAnalysisLabel, 0, 3);
                grid.add(filePathForStaticAnalysisField, 1, 3);
                grid.add(browseFilePathForStatAnalysisButton, 2, 3);
            } else {
                grid.add(infoKey, 0, 3);
                grid.add(keyLabel, 0, 4);
                grid.add(keyField, 1, 4);
            }
        });


        grid.add(commandLabel, 0, 0);
        grid.add(commandComboBox, 1, 0);
        grid.add(infoForCommand, 1, 1);
        grid.add(infoForPath, 0, 1);
        grid.add(filePathLabel, 0, 2);
        grid.add(filePathField, 1, 2);
        grid.add(browseFilePathButton, 2, 2);
        grid.add(infoLabel, 3, 3);
        grid.add(executeButton, 1, 5);

        GridPane.setColumnSpan(infoLabel, GridPane.REMAINING);

        Scene scene = new Scene(grid, 900, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
