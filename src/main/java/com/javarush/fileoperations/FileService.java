/**
 * The `FileService` class provides methods for encrypting and decrypting text files using the Caesar cipher,
 * as well as performing brute force decryption and analysis on encrypted files. It also includes utility
 * methods for checking the existence of files and validating numeric strings.
 * <p>
 * This class includes the following methods:
 * <p>
 * 1. `fileExists(String filePath)`: Checks if a file exists at the specified path.
 * <p>
 * 2. `isNumeric(String str)`: Checks if a given string represents a numeric value.
 */
package com.javarush.fileoperations;


import java.io.File;

public class FileService {
    private FileService() {
    }

    /**
     * Checks if a file exists at the specified path.
     *
     * @param filePath The path of the file to be checked for existence.
     * @return true if the file exists; otherwise, false.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Appends an operation tag to the file name to create a new file name with the specified operation.
     * The operation is enclosed in square brackets and added before the file extension.
     *
     * @param filePath  The path of the file to be modified.
     * @param operation The operation tag to be appended.
     * @return The new file name with the appended operation tag.
     */

    public static String appendOperationToFileName(String filePath, String operation) {
        int dotIndex = filePath.lastIndexOf('.');
        String fileName = filePath.substring(0, dotIndex);
        String fileExtension = filePath.substring(dotIndex);
        return fileName + "[" + operation + "]" + fileExtension;
    }

}