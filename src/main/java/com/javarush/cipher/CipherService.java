package com.javarush.cipher;

import com.javarush.brute.BruteForce;
import com.javarush.fileoperations.CustomFileReader;
import com.javarush.fileoperations.CustomFileWriter;
import com.javarush.fileoperations.FileService;

public class CipherService {
    private final CustomFileWriter fileWriter = new CustomFileWriter();
    private final CustomFileReader fileReader = new CustomFileReader();
    private final CaesarCipher caesarCipher = new CaesarCipher();
    private final BruteForce bruteForce = new BruteForce();

    /**
     * Encrypts the content of a file using the Caesar cipher and saves the encrypted content to a new file.
     *
     * @param filePath The path of the file to be encrypted.
     * @param key      The encryption key for the Caesar cipher.
     */
    public void encryptFile(String filePath, int key) {
        String content = fileReader.readFile(filePath);
        String encryptedContent = caesarCipher.encrypt(content, key);
        String encryptedFilePath = FileService.appendOperationToFileName(filePath, "ENCRYPTED");
        fileWriter.writeFile(encryptedFilePath, encryptedContent);
        System.out.println("File encrypted successfully. Encrypted file saved at: " + encryptedFilePath);
    }

    /**
     * Decrypts the content of a file using the Caesar cipher and saves the decrypted content to a new file.
     *
     * @param filePath The path of the file to be decrypted.
     * @param key      The decryption key for the Caesar cipher.
     */
    public void decryptFile(String filePath, int key) {
        String content = fileReader.readFile(filePath);
        String decryptedContent = caesarCipher.decrypt(content, key);
        String decryptedFilePath = FileService.appendOperationToFileName(filePath, "DECRYPTED");
        fileWriter.writeFile(decryptedFilePath, decryptedContent);
        System.out.println("File decrypted successfully. Decrypted file saved at: " + decryptedFilePath);
    }

    /**
     * Performs brute force decryption and analysis on the content of a file using the Caesar cipher and static
     * analysis of expected letter percentages.
     *
     * @param filePath                  The path of the file to be decrypted and analyzed.
     * @param filePathForStaticAnalysis The path of the file containing expected letter percentages for analysis.
     */
    public int decryptAnalysisBF(String filePath, String filePathForStaticAnalysis) {
        String text = fileReader.readFile(filePath);
        String textForAnalysis = fileReader.readFile(filePathForStaticAnalysis);
        BruteForce.BruteForceResult result = bruteForce.bruteForceDecrypt(text, textForAnalysis);
        String bruteDecryptedContent = result.getDecryptedText();
        int key = result.getKey();
        String bruteDecryptedFilePath = FileService.appendOperationToFileName(filePath, ("BRUTEFORCE=" + key));
        fileWriter.writeFile(bruteDecryptedFilePath, bruteDecryptedContent);
        System.out.println("File decrypted with bruteforce. Decrypted file saved at:  " + bruteDecryptedFilePath);
        return key;
    }

    /**
     * Performs brute force decryption on the content of a file using the Caesar cipher.
     *
     * @param filePath The path of the file to be decrypted.
     */
    public int decryptBF(String filePath) {
        String text = fileReader.readFile(filePath);
        BruteForce.BruteForceResult result = bruteForce.bruteForceDecrypt(text);
        String bruteDecryptedContent = result.getDecryptedText();
        int key = result.getKey();
        String bruteDecryptedFilePath = FileService.appendOperationToFileName(filePath, ("BRUTEFORCE=" + key));
        fileWriter.writeFile(bruteDecryptedFilePath, bruteDecryptedContent);
        return key;
    }

}
