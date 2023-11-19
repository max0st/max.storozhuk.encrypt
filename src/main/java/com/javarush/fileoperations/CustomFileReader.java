/**
 * The `CustomFileReader` class provides a method for reading the content of a file using a buffered reader.
 * <p>
 * This class includes the following method:
 * <p>
 * 1. `readFile(String filePath)`: Reads the content of the file at the given path and returns it as a string.
 */
package com.javarush.fileoperations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CustomFileReader {
    /**
     * Reads the content of the file at the given path and returns it as a string.
     *
     * @param filePath The path of the file to read.
     * @return The content of the file as a string.
     */
    protected static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        return content.toString();
    }
}