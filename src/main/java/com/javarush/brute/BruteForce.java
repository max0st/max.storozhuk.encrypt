/**
 * The `BruteForce` class provides methods for performing brute force decryption on text encrypted
 * using the Caesar cipher. It aims to find the most plausible decryption by trying all possible keys
 * and evaluating the results based on frequency analysis or static analysis of expected letter percentages.
 * <p>
 * This class includes the following methods:
 * <p>
 * 1. `bruteForceDecrypt(String encryptedText)`: Performs brute force decryption on the input encrypted text
 * and returns the most plausible result along with the corresponding key. It uses frequency analysis to
 * determine the plausibility of each decryption attempt.
 * <p>
 * 2. `bruteForceDecrypt(String encryptedText, String filePathForStaticAnalysis)`: Performs brute force decryption
 * on the input encrypted text using static analysis of expected letter percentages from a specified file.
 * It returns the most plausible result along with the corresponding key.
 * <p>
 * 3. `findKey(String encryptedText)`:Finds the most plausible key for brute force decryption based on frequency analysis.
 * It iterates through all possible keys and evaluates the plausibility of each decryption attempt.
 * <p>
 * 4. `findKey(String encryptedText, String filePathForStaticAnalysis)`: Finds the most plausible key for brute force decryption based on static analysis of expected
 * letter percentages from a specified file. It iterates through all possible keys and evaluates the plausibility of each decryption attempt.
 * <p>
 * 5. `BruteForceResult`: A nested class representing the result of the brute force decryption, containing
 * the decrypted text and the key used for decryption.
 * <p>
 * <p>
 * Note: The class relies on the `CaesarCipher` class for decryption and assumes that the
 * `FrequencyAnalysis` and `ExpectedPercentages` classes provide the necessary analysis methods.
 */
package com.javarush.brute;

import com.javarush.cipher.AlphabetDeterminer;
import com.javarush.cipher.CaesarCipher;

import java.util.Map;

public class BruteForce {
    private final CaesarCipher caesarCipher = new CaesarCipher();
    private final ExpectedPercentages expectedPercentages = new ExpectedPercentages();
    private final FrequencyAnalysis frequencyAnalysis = new FrequencyAnalysis();

    /**
     * Performs brute force decryption on the input encrypted text and returns the most plausible result
     * along with the corresponding key. It uses frequency analysis to determine the plausibility of each
     * decryption attempt.
     *
     * @param encryptedText The text to be decrypted.
     * @return The result of the brute force decryption, including the decrypted text and the decryption key.
     */
    public BruteForceResult bruteForceDecrypt(String encryptedText) {
        int mostPlausibleKey = findKey(encryptedText);
        String decryptedText = caesarCipher.decrypt(encryptedText, mostPlausibleKey);
        return new BruteForceResult(decryptedText, mostPlausibleKey);
    }

    /**
     * Performs brute force decryption on the input encrypted text using static analysis of expected
     * letter percentages from a specified file. It returns the most plausible result along with the
     * corresponding key.
     *
     * @param encryptedText             The text to be decrypted.
     * @param filePathForStaticAnalysis The file path containing expected letter percentages for static analysis.
     * @return The result of the brute force decryption, including the decrypted text and the decryption key.
     */

    public BruteForceResult bruteForceDecrypt(String encryptedText, String filePathForStaticAnalysis) {
        int mostPlausibleKey = findKey(encryptedText, filePathForStaticAnalysis);
        String decryptedText = caesarCipher.decrypt(encryptedText, mostPlausibleKey);
        return new BruteForceResult(decryptedText, mostPlausibleKey);
    }

    /**
     * Finds the most plausible key for brute force decryption based on frequency analysis.
     * It iterates through all possible keys and evaluates the plausibility of each decryption attempt.
     *
     * @param encryptedText The text to be decrypted.
     * @return The most plausible key for decryption.
     */
    private int findKey(String encryptedText) {
        int alphabetLength = (AlphabetDeterminer.determineAlphabet(encryptedText).length()) - AlphabetDeterminer.getAdditionalCases().length();
        int bestKey = 0;
        double bestPlausibility = Double.POSITIVE_INFINITY;

        for (int key = 1; key < alphabetLength + 1; key++) {
            String decryptedText = caesarCipher.decrypt(encryptedText, key);
            double plausibility = frequencyAnalysis.calculateProbability(decryptedText);

            if (plausibility < bestPlausibility) {
                bestPlausibility = plausibility;
                bestKey = key;
            }
        }
        return bestKey;
    }

    /**
     * Finds the most plausible key for brute force decryption based on static analysis of expected
     * letter percentages from a specified file. It iterates through all possible keys and evaluates
     * the plausibility of each decryption attempt.
     *
     * @param encryptedText             The text to be decrypted.
     * @param filePathForStaticAnalysis The file path containing expected letter percentages for static analysis.
     * @return The most plausible key for decryption.
     */

    private int findKey(String encryptedText, String filePathForStaticAnalysis) {
        int alphabetLength = (AlphabetDeterminer.determineAlphabet(encryptedText).length()) - AlphabetDeterminer.getAdditionalCases().length();
        int bestKey = 0;
        double bestPlausibility = Double.POSITIVE_INFINITY;

        Map<Character, Double> letterPercentages = expectedPercentages.calculateLetterPercentages(filePathForStaticAnalysis);

        for (int key = 1; key < alphabetLength + 1; key++) {
            String decryptedText = caesarCipher.decrypt(encryptedText, key);
            double plausibility = frequencyAnalysis.calculateProbability(decryptedText, letterPercentages);

            if (plausibility < bestPlausibility) {
                bestPlausibility = plausibility;
                bestKey = key;
            }
        }

        return bestKey;
    }

    /**
     * A nested class representing the result of the brute force decryption.
     * It includes the decrypted text and the key used for decryption.
     */
    public static class BruteForceResult {
        private final String decryptedText;
        private final int key;

        private BruteForceResult(String stringValue, int key) {
            this.decryptedText = stringValue;
            this.key = key;
        }

        public String getDecryptedText() {
            return decryptedText;
        }

        public int getKey() {
            return key;
        }
    }

}
