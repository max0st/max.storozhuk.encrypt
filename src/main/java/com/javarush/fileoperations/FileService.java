/**
 * The `FileService` class provides methods for encrypting and decrypting text files using the Caesar cipher,
 * as well as performing brute force decryption and analysis on encrypted files. It also includes utility
 * methods for checking the existence of files and validating numeric strings.
 * <p>
 * This class includes the following methods:
 * <p>
 * 1. `encryptFile(String filePath, int key)`: Encrypts the content of a file using the Caesar cipher and saves
 * the encrypted content to a new file.
 * <p>
 * 2. `decryptFile(String filePath, int key)`: Decrypts the content of a file using the Caesar cipher and saves
 * the decrypted content to a new file.
 * <p>
 * 3. `decryptAnalysisBF(String filePath, String filePathForStaticAnalysis)`: Performs brute force decryption and
 * analysis on the content of a file using the Caesar cipher and static analysis of expected letter percentages.
 * <p>
 * 4. `decryptBF(String filePath)`: Performs brute force decryption on the content of a file using the Caesar cipher.
 * <p>
 * 5. `appendOperationToFileName(String filePath, String operation)`: Appends an operation tag to the file name to create a new file name with the specified operation. The operation is enclosed in square brackets and added before the file extension.
 * <p>
 * 6. `fileExists(String filePath)`: Checks if a file exists at the specified path.
 * <p>
 * 7. `isNumeric(String str)`: Checks if a given string represents a numeric value.
 */
package com.javarush.fileoperations;


import com.javarush.brute.BruteForce;
import com.javarush.cipher.CaesarCipher;

import java.io.File;


public class FileService {

    /**
     * Encrypts the content of a file using the Caesar cipher and saves the encrypted content to a new file.
     *
     * @param filePath The path of the file to be encrypted.
     * @param key      The encryption key for the Caesar cipher.
     */
    public static void encryptFile(String filePath, int key) {
        String content = CustomFileReader.readFile(filePath);
        String encryptedContent = CaesarCipher.encrypt(content, key);
        String encryptedFilePath = appendOperationToFileName(filePath, "ENCRYPTED");
        CustomFileWriter.writeFile(encryptedFilePath, encryptedContent);
        System.out.println("File encrypted successfully. Encrypted file saved at: " + encryptedFilePath);
    }

    /**
     * Decrypts the content of a file using the Caesar cipher and saves the decrypted content to a new file.
     *
     * @param filePath The path of the file to be decrypted.
     * @param key      The decryption key for the Caesar cipher.
     */
    public static void decryptFile(String filePath, int key) {
        String content = CustomFileReader.readFile(filePath);
        String decryptedContent = CaesarCipher.decrypt(content, key);
        String decryptedFilePath = appendOperationToFileName(filePath, "DECRYPTED");
        CustomFileWriter.writeFile(decryptedFilePath, decryptedContent);
        System.out.println("File decrypted successfully. Decrypted file saved at: " + decryptedFilePath);
    }

    /**
     * Performs brute force decryption and analysis on the content of a file using the Caesar cipher and static
     * analysis of expected letter percentages.
     *
     * @param filePath                  The path of the file to be decrypted and analyzed.
     * @param filePathForStaticAnalysis The path of the file containing expected letter percentages for analysis.
     */
    public static int decryptAnalysisBF(String filePath, String filePathForStaticAnalysis) {
        String text = CustomFileReader.readFile(filePath);
        String textForAnalysis = CustomFileReader.readFile(filePathForStaticAnalysis);
        BruteForce.BruteForceResult result = BruteForce.bruteForceDecrypt(text, textForAnalysis);
        String bruteDecryptedContent = result.getDecryptedText();
        int key = result.getKey();
        String bruteDecryptedFilePath = appendOperationToFileName(filePath, ("BRUTEFORCE=" + key));
        CustomFileWriter.writeFile(bruteDecryptedFilePath, bruteDecryptedContent);
        System.out.println("File decrypted with bruteforce. Decrypted file saved at:  " + bruteDecryptedFilePath);
        return key;
    }

    /**
     * Performs brute force decryption on the content of a file using the Caesar cipher.
     *
     * @param filePath The path of the file to be decrypted.
     */
    public static int decryptBF(String filePath) {
        String text = CustomFileReader.readFile(filePath);
        BruteForce.BruteForceResult result = BruteForce.bruteForceDecrypt(text);
        String bruteDecryptedContent = result.getDecryptedText();
        int key = result.getKey();
        String bruteDecryptedFilePath = appendOperationToFileName(filePath, ("BRUTEFORCE=" + key));
        CustomFileWriter.writeFile(bruteDecryptedFilePath, bruteDecryptedContent);
        return key;
    }

    /**
     * Appends an operation tag to the file name to create a new file name with the specified operation.
     * The operation is enclosed in square brackets and added before the file extension.
     *
     * @param filePath  The path of the file to be modified.
     * @param operation The operation tag to be appended.
     * @return The new file name with the appended operation tag.
     */
    private static String appendOperationToFileName(String filePath, String operation) {
        int dotIndex = filePath.lastIndexOf('.');
        String fileName = filePath.substring(0, dotIndex);
        String fileExtension = filePath.substring(dotIndex);
        return fileName + "[" + operation + "]" + fileExtension;
    }

    /**
     * Checks if a file exists at the specified path.
     *
     * @param filePath The path of the file to be checked for existence.
     * @return True if the file exists; otherwise, false.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Checks if a given string represents a numeric value.
     *
     * @param str The string to be checked for numeric representation.
     * @return True if the string is numeric; otherwise, false.
     */
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}