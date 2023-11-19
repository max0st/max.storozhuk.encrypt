/**
 * The `CustomFileWriter` class provides a method for writing content to a file using a buffered writer.
 * <p>
 * This class includes the following method:
 * <p>
 * 1. `writeFile(String filePath, String content)`: Writes the specified content to the file at the given path.
 */
package com.javarush.fileoperations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class CustomFileWriter {
    /**
     * Writes the specified content to the file at the given path.
     *
     * @param filePath The path of the file to write the content to.
     * @param content  The content to be written to the file.
     */
    protected static void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
