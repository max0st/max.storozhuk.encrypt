package com.javarush.gui;

import com.javarush.fileoperations.FileService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUIController {


    protected void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void showSuccessDialog(String operation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(operation + " completed successfully!");
        alert.showAndWait();
    }

    protected void showSuccessDialog(String operation, int key) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(operation + " completed successfully! Probable key is" + key);
        alert.showAndWait();
    }

    protected void initialize(Stage primaryStage) {

        Label commandLabel = createLabel("Command:");
        commandLabel.setVisible(true);

        Label filePathLabel = createLabel("File Path:");

        Label keyLabel = createLabel("Key:");

        Label filePathForStaticAnalysisLabel = createLabel("File for Static Analysis:");

        Label infoLabel = createLabel("If the field is empty,\n the default letter frequencies will be used");

        Label infoForCommand = createLabel("Choose the command to perform actions");

        Label infoKey = createLabel("Provide the key:");

        Label infoForPath = createLabel("Browse the path to the file.txt that you want to work with:");

        TextField filePathField = createTextField();

        TextField keyField = createTextField();

        TextField filePathForStaticAnalysisField = createTextField();

        FileChooser fileChooser = createFileChooserTXT();

        Button browseFilePathButton = createBrowseButton(filePathField, fileChooser, primaryStage);

        Button browseFilePathForStatAnalysisButton = createBrowseButton(filePathForStaticAnalysisField, fileChooser, primaryStage);

        ComboBox<String> commandComboBox = createCommandBox();

        Button executeButton = createExecuteButton(commandComboBox, filePathField, keyField, filePathForStaticAnalysisField);

        GridPane grid = createGrid();

        commandBoxActionSet(filePathLabel, keyLabel, filePathForStaticAnalysisLabel, infoLabel, infoForCommand, infoKey, infoForPath, filePathField, keyField, filePathForStaticAnalysisField, browseFilePathButton, browseFilePathForStatAnalysisButton, commandComboBox, executeButton, grid);

        addGridElements(commandLabel, filePathLabel, infoLabel, infoForCommand, infoForPath, filePathField, browseFilePathButton, commandComboBox, executeButton, grid);

        Scene scene = new Scene(grid, 900, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    protected GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        return grid;
    }

    protected ComboBox<String> createCommandBox() {
        ComboBox<String> commandComboBox = new ComboBox<>();
        commandComboBox.getItems().addAll("ENCRYPT", "DECRYPT", "BRUTE_FORCE");
        return commandComboBox;
    }

    protected Label createLabel(String labelName) {
        Label label = new Label(labelName);
        label.setVisible(false);
        return label;
    }

    protected TextField createTextField() {
        TextField field = new TextField();
        field.setVisible(false);
        return field;
    }

    protected FileChooser createFileChooserTXT() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser;
    }

    protected Button createBrowseButton(TextField filePathField, FileChooser fileChooser, Stage primaryStage) {
        Button browseFilePathButton = new Button("Browse");
        browseFilePathButton.setOnAction(e -> filePathField.setText(fileChooser.showOpenDialog(primaryStage).getAbsolutePath()));
        browseFilePathButton.setVisible(false);
        return browseFilePathButton;
    }

    protected Button createExecuteButton(ComboBox<String> commandComboBox, TextField filePathField, TextField keyField, TextField filePathForStaticAnalysisField) {
        Button executeButton = new Button("Execute");
        executeButton.setVisible(false);
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
                case "ENCRYPT" -> {
                    FileService.encryptFile(filePath, Integer.parseInt(key));
                    showSuccessDialog(command);
                }
                case "DECRYPT" -> {
                    FileService.decryptFile(filePath, Integer.parseInt(key));
                    showSuccessDialog(command);
                }
                case "BRUTE_FORCE" -> {
                    int decryptedKey;
                    if (!filePathForStaticAnalysis.isEmpty()) {
                        decryptedKey = FileService.decryptAnalysisBF(filePath, filePathForStaticAnalysis);
                    } else {
                        decryptedKey = FileService.decryptBF(filePath);
                    }
                    showSuccessDialog(command, decryptedKey);
                }
                default -> showErrorDialog("Invalid command.");
            }


        });
        return executeButton;
    }

    protected void commandBoxActionSet(Label filePathLabel, Label keyLabel, Label filePathForStaticAnalysisLabel, Label infoLabel, Label infoForCommand, Label infoKey, Label infoForPath, TextField filePathField, TextField keyField, TextField filePathForStaticAnalysisField, Button browseFilePathButton, Button browseFilePathForStatAnalysisButton, ComboBox<String> commandComboBox, Button executeButton, GridPane grid) {
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
    }

    protected void addGridElements(Label commandLabel, Label filePathLabel, Label infoLabel, Label infoForCommand, Label infoForPath, TextField filePathField, Button browseFilePathButton, ComboBox<String> commandComboBox, Button executeButton, GridPane grid) {
        grid.add(commandLabel, 0, 0);
        grid.add(commandComboBox, 1, 0);
        grid.add(infoForCommand, 1, 1);
        grid.add(infoForPath, 0, 1);
        grid.add(filePathLabel, 0, 2);
        grid.add(filePathField, 1, 2);
        grid.add(browseFilePathButton, 2, 2);
        grid.add(infoLabel, 3, 3);
        grid.add(executeButton, 1, 5);
    }


}
