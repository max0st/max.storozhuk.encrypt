/**
 * The `FrequencyAnalysis` class provides methods for calculating the plausibility of decrypted text based on
 * frequency analysis. It supports two calculation modes: one using the expected letter percentages obtained
 * from the text itself and another using expected letter percentages from a provided map.
 * <p>
 * This class includes the following methods:
 * <p>
 * 1. `calculateProbability(String decryptedText)`: Calculates the plausibility of decrypted text using
 * frequency analysis and expected letter percentages obtained from the text itself.
 * <p>
 * 2. `calculateProbability(String decryptedText, Map<Character, Double> letterPercentages)`: Calculates
 * the plausibility of decrypted text using frequency analysis and expected letter percentages from a map.
 * <p>
 * 3. `calculateFrequency(String text)`: Calculates the frequency of each letter in the input text and
 * returns an array representing the distribution of letters in the alphabet.
 * <p>
 * Note: This class assumes that the `AlphabetDeterminer` class provides the alphabet for the input text,
 * and it relies on the `ExpectedPercentages` class for obtaining expected letter percentages.
 */
package com.javarush.brute;

import com.javarush.cipher.AlphabetDeterminer;

import java.util.Map;

public class FrequencyAnalysis {
    /**
     * Calculates the plausibility of decrypted text using frequency analysis and expected letter percentages
     * obtained from the text itself.
     *
     * @param decryptedText The decrypted text for plausibility calculation.
     * @return The plausibility score based on frequency analysis.
     */
    static double calculateProbability(String decryptedText) {
        String alphabet = AlphabetDeterminer.determineAlphabet(decryptedText);
        int alphabetLength = alphabet.length() - AlphabetDeterminer.ADDITIONAL_CASES.length();
        int[] decryptedFrequency = calculateFrequency(decryptedText);
        double plausibility = 0.0;

        for (int i = 0; i < alphabetLength; i++) {
            double expectedPercentage = ExpectedPercentages.getExpectedPercentage(alphabet.charAt(i), decryptedText);
            double difference = decryptedFrequency[i] - expectedPercentage;
            plausibility += Math.pow(difference, 2);
        }

        return plausibility;
    }

    /**
     * Calculates the plausibility of decrypted text using frequency analysis and expected letter percentages
     * from a provided map.
     *
     * @param decryptedText     The decrypted text for plausibility calculation.
     * @param letterPercentages The map containing expected letter percentages.
     * @return The plausibility score based on frequency analysis.
     */
    static double calculateProbability(String decryptedText, Map<Character, Double> letterPercentages) {
        String alphabet = AlphabetDeterminer.determineAlphabet(decryptedText);
        int alphabetLength = alphabet.length() - AlphabetDeterminer.ADDITIONAL_CASES.length();
        int[] decryptedFrequency = FrequencyAnalysis.calculateFrequency(decryptedText);
        double plausibility = 0.0;

        for (int i = 0; i < alphabetLength; i++) {
            char letter = alphabet.charAt(i);
            double expectedPercentage = letterPercentages.getOrDefault(letter, 0.0);
            double difference = decryptedFrequency[i] - expectedPercentage;
            plausibility += Math.pow(difference, 2);
        }

        return plausibility;
    }

    /**
     * Calculates the frequency of each letter in the input text and returns an array representing
     * the distribution of letters in the alphabet.
     *
     * @param text The input text for frequency analysis.
     * @return An array representing the distribution of letters in the alphabet.
     */

    static int[] calculateFrequency(String text) {
        String alphabet = AlphabetDeterminer.determineAlphabet(text);
        int alphabetLength = alphabet.length();
        int[] frequency = new int[alphabetLength];
        char[] textChars = text.toCharArray();
        int totalCharacters = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = textChars[i];
            int index = alphabet.indexOf(Character.toLowerCase(ch));
            if (index != -1) {
                frequency[index]++;
                totalCharacters++;
            }
        }

        for (int i = 0; i < alphabetLength; i++) {
            frequency[i] = (int) Math.round((frequency[i] / (double) totalCharacters) * 100);
        }

        return frequency;
    }
}
