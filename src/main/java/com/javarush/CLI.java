/**
 * The `CLI` (Command Line Interface) class provides a simple command-line interface for interacting with the
 * file encryption and decryption operations defined in the `FileService` class. It allows users to input commands,
 * file paths, and keys through the console.
 * <p>
 * This class includes the following methods:
 * <p>
 * 1. `getUserInput()`: Prompts the user to enter a command (ENCRYPT, DECRYPT, BRUTE_FORCE), file path, and key
 * (for encryption and decryption operations) or file path and optional file path for static analysis (for
 * brute force decryption with analysis).
 * <p>
 * 2. `getFilePath()`: Prompts the user to enter a file path.
 * <p>
 * 3. `getFilePathForStaticAnalysis()`: Prompts the user to enter a file path for static analysis (used in
 * BRUTE_FORCE command).
 * <p>
 * 4. `getKey()`: Prompts the user to enter a key for encryption and decryption operations.
 * <p>
 * 5. `runFromArgs(String[] args)`: Runs the file operations based on the command-line arguments passed to the
 * application.
 * <p>
 * 6. `runFromCLI()`: Prompts the user for input and executes the corresponding file operation based on the entered
 * command.
 */
package com.javarush;

import com.javarush.cipher.CipherService;

import java.util.Scanner;

public class CLI {

    private final Scanner scanner = new Scanner(System.in);
    private final CipherService cipherService = new CipherService();


    public String[] getUserInput() {
        System.out.println("Enter command (ENCRYPT, DECRYPT, BRUTE_FORCE):");
        String command = scanner.next().toUpperCase();

        if ("BRUTE_FORCE".equals(command)) {
            System.out.println("Do you want to provide FilePath ForStatic Analysis? Yes/No");
            if ((scanner.next()).equalsIgnoreCase("no")) {
                return new String[]{command, getFilePath()};
            } else {
                return new String[]{command, getFilePath(), getFilePathForStaticAnalysis()};
            }
        } else {
            return new String[]{command, getFilePath(), getKey()};
        }
    }

    private String getFilePath() {
        System.out.println("Enter the path to the file you want to work with :");
        return scanner.next();
    }

    private String getFilePathForStaticAnalysis() {
        System.out.println("Enter file for static analysis path:");
        return scanner.next();
    }

    private String getKey() {
        System.out.println("Enter key:");
        return scanner.next();
    }

    public void runFromArgs(String[] args) {

        String command = args[0].toUpperCase();
        String filePath = args[1];

        switch (command) {
            case "ENCRYPT" -> {
                if (args.length == 3) {
                    int key = Integer.parseInt(args[2]);
                    cipherService.encryptFile(filePath, key);
                } else {
                    System.out.println("Invalid arguments for ENCRYPT command.");
                }
            }
            case "DECRYPT" -> {
                if (args.length == 3) {
                    int key = Integer.parseInt(args[2]);
                    cipherService.decryptFile(filePath, key);
                } else {
                    System.out.println("Invalid arguments for DECRYPT command.");
                }
            }
            case "BRUTE_FORCE" -> {
                if (args.length == 3) {
                    String filePathForStaticAnalysis = args[2];
                    cipherService.decryptAnalysisBF(filePath, filePathForStaticAnalysis);
                } else if (args.length == 2) {
                    cipherService.decryptBF(filePath);
                } else System.out.println("Invalid arguments for BRUTEFORCE command.");
            }
            default -> System.out.println("Invalid command.");
        }
    }

    public void runFromCLI() {
        String[] userInput = getUserInput();

        if (userInput.length > 0) {
            runFromArgs(userInput);
        }
    }
}
