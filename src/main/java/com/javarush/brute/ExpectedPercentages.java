/**
 * The `ExpectedPercentages` class provides methods for obtaining expected letter percentages for frequency
 * analysis in both English and Ukrainian alphabets. It also includes methods to calculate letter percentages
 * based on the frequency of letters in the input text.
 * <p>
 * This class includes the following methods and constants:
 * <p>
 * 1. `getExpectedPercentage(char letter, String decryptedText)`: Gets the expected percentage of a letter
 * in the decrypted text based on the language detected.
 * <p>
 * 2. `calculateLetterPercentages(String textForAnalysis)`: Calculates the letter percentages based on the
 * frequency of letters in the input text.
 * <p>
 * 3. `createExpectedPercentagesEnglish()`: Creates a map of expected letter percentages for the English alphabet.
 * <p>
 * 4. `createExpectedPercentagesUkrainian()`: Creates a map of expected letter percentages for the Ukrainian alphabet.
 * <p>
 * <p>
 * Note: This class assumes that the `AlphabetDeterminer` class provides the alphabet for the input text.
 */
package com.javarush.brute;

import com.javarush.cipher.AlphabetDeterminer;

import java.util.HashMap;
import java.util.Map;

public class ExpectedPercentages {

    private static final String UKRAINIAN_ALPHABET = AlphabetDeterminer.getUkrainianAlphabet();
    private static final String ENGLISH_ALPHABET = AlphabetDeterminer.getEnglishAlphabet();
    private static final Map<Character, Double> ENGLISH_EXPECTED_PERCENTAGES = ExpectedPercentages.createExpectedPercentagesEnglish();
    private static final Map<Character, Double> UKRAINIAN_EXPECTED_PERCENTAGES = ExpectedPercentages.createExpectedPercentagesUkrainian();

    /**
     * Gets the expected percentage of a letter in the decrypted text based on the language detected.
     *
     * @param letter        The letter for which the expected percentage is to be calculated.
     * @param decryptedText The decrypted text used for language detection.
     * @return The expected percentage of the given letter.
     */
    protected static double getExpectedPercentage(char letter, String decryptedText) {
        char lowerCaseLetter = Character.toLowerCase(letter);
        String alphabet = AlphabetDeterminer.determineAlphabet(decryptedText);
        if (alphabet.equals(ENGLISH_ALPHABET)) {
            return ENGLISH_EXPECTED_PERCENTAGES.getOrDefault(lowerCaseLetter, 0.0);
        } else return UKRAINIAN_EXPECTED_PERCENTAGES.getOrDefault(lowerCaseLetter, 0.0);
    }

    /**
     * Calculates the letter percentages based on the frequency of letters in the input text.
     *
     * @param textForAnalysis The input text for frequency analysis.
     * @return A map containing letter percentages for each letter in the text.
     */
    protected static Map<Character, Double> calculateLetterPercentages(String textForAnalysis) {
        int[] frequency = FrequencyAnalysis.calculateFrequency(textForAnalysis);
        int totalCharacters = textForAnalysis.length();

        Map<Character, Double> percentages = new HashMap<>();
        String alphabet = AlphabetDeterminer.determineAlphabet(textForAnalysis);

        for (int i = 0; i < alphabet.length(); i++) {
            char letter = alphabet.charAt(i);
            int index = alphabet.indexOf(Character.toLowerCase(letter));
            if (index != -1) {
                double percentage = (frequency[index] / (double) totalCharacters) * 100;
                percentages.put(letter, percentage);
            }
        }

        return percentages;
    }

    /**
     * Creates a map of expected letter percentages for the English alphabet.
     */

    protected static Map<Character, Double> createExpectedPercentagesEnglish() {
        Map<Character, Double> expectedPercentages = new HashMap<>();
        expectedPercentages.put('a', 8.17);
        expectedPercentages.put('b', 1.49);
        expectedPercentages.put('c', 2.78);
        expectedPercentages.put('d', 4.25);
        expectedPercentages.put('e', 12.70);
        expectedPercentages.put('f', 2.23);
        expectedPercentages.put('g', 2.02);
        expectedPercentages.put('h', 6.09);
        expectedPercentages.put('i', 6.97);
        expectedPercentages.put('j', 0.15);
        expectedPercentages.put('k', 0.77);
        expectedPercentages.put('l', 4.03);
        expectedPercentages.put('m', 2.41);
        expectedPercentages.put('n', 6.75);
        expectedPercentages.put('o', 7.51);
        expectedPercentages.put('p', 1.93);
        expectedPercentages.put('q', 0.10);
        expectedPercentages.put('r', 5.99);
        expectedPercentages.put('s', 6.33);
        expectedPercentages.put('t', 9.06);
        expectedPercentages.put('u', 2.76);
        expectedPercentages.put('v', 0.98);
        expectedPercentages.put('w', 2.36);
        expectedPercentages.put('x', 0.15);
        expectedPercentages.put('y', 1.97);
        expectedPercentages.put('z', 0.07);
        return expectedPercentages;
    }

    /**
     * Creates a map of expected letter percentages for the Ukrainian alphabet.
     */
    protected static Map<Character, Double> createExpectedPercentagesUkrainian() {
        Map<Character, Double> expectedPercentages = new HashMap<>();
        expectedPercentages.put('а', 7.2);
        expectedPercentages.put('б', 1.7);
        expectedPercentages.put('в', 5.2);
        expectedPercentages.put('г', 1.6);
        expectedPercentages.put('ґ', 0.01);
        expectedPercentages.put('д', 3.5);
        expectedPercentages.put('е', 1.7);
        expectedPercentages.put('є', 0.8);
        expectedPercentages.put('ж', 0.9);
        expectedPercentages.put('з', 2.3);
        expectedPercentages.put('и', 6.1);
        expectedPercentages.put('і', 5.7);
        expectedPercentages.put('ї', 0.6);
        expectedPercentages.put('й', 0.8);
        expectedPercentages.put('к', 3.5);
        expectedPercentages.put('л', 3.6);
        expectedPercentages.put('м', 3.1);
        expectedPercentages.put('н', 6.5);
        expectedPercentages.put('о', 9.4);
        expectedPercentages.put('п', 2.9);
        expectedPercentages.put('р', 4.7);
        expectedPercentages.put('с', 4.1);
        expectedPercentages.put('т', 5.5);
        expectedPercentages.put('у', 4.0);
        expectedPercentages.put('ф', 0.1);
        expectedPercentages.put('х', 1.2);
        expectedPercentages.put('ц', 0.6);
        expectedPercentages.put('ч', 1.8);
        expectedPercentages.put('ш', 1.2);
        expectedPercentages.put('щ', 0.1);
        expectedPercentages.put('ь', 2.9);
        expectedPercentages.put('ю', 0.4);
        expectedPercentages.put('я', 2.9);

        return expectedPercentages;
    }

}
