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

            // Перевірка введених даних
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

            if (!FileService.fileExists(filePathForStaticAnalysis)) {
                showErrorDialog("File for static analysis not found at the specified path.");
                return;
            }

            switch (command) {
                case "ENCRYPT":
                    FileService.encryptFile(filePath, Integer.parseInt(key));
                    break;
                case "DECRYPT":
                    FileService.decryptFile(filePath, Integer.parseInt(key));
                    break;
                case "BRUTE_FORCE":
                    if (!filePathForStaticAnalysis.isEmpty()) {
                        FileService.decryptAnalysisBF(filePath, filePathForStaticAnalysis);
                    } else {
                        FileService.decryptBF(filePath);
                    }
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
            filePathForStaticAnalysisLabel.setVisible(isBruteForce);
            filePathForStaticAnalysisField.setVisible(isBruteForce);
            infoLabel.setVisible(isBruteForce);
            executeButton.setVisible(true);
            browseFilePathButton.setVisible(true);
            browseFilePathForStatAnalysisButton.setVisible(isBruteForce);
            infoForCommand.setVisible(false);


            if (isBruteForce) {
                grid.getChildren().removeAll(keyLabel, keyField);
                grid.add(filePathForStaticAnalysisLabel, 0, 2);
                grid.add(filePathForStaticAnalysisField, 1, 2);
            } else {
                grid.getChildren().removeAll(filePathForStaticAnalysisLabel, filePathForStaticAnalysisField, infoLabel);
                grid.add(keyLabel, 0, 2);
                grid.add(keyField, 1, 2);
            }
        });


        grid.add(commandLabel, 0, 0);
        grid.add(commandComboBox, 1, 0);
        grid.add(infoForCommand,1,1);
        grid.add(filePathLabel, 0, 1);
        grid.add(filePathField, 1, 1);
        grid.add(browseFilePathButton, 2, 1);
        grid.add(filePathForStaticAnalysisLabel, 0, 2);
        grid.add(filePathForStaticAnalysisField, 1, 2);
        grid.add(browseFilePathForStatAnalysisButton, 2 ,2);
        grid.add(infoLabel, 3, 2);
        grid.add(keyLabel, 0, 2);
        grid.add(keyField, 1, 2);
        grid.add(executeButton, 1, 4);

        GridPane.setColumnSpan(infoLabel, GridPane.REMAINING);

        Scene scene = new Scene(grid, 650, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
