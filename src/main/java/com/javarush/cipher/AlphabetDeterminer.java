/**
 * The `AlphabetDeterminer` class is responsible for determining the alphabet based on the language
 * detected in the input text. It supports English and Ukrainian alphabets, including additional
 * characters specified in the constant ADDITIONAL_CASES.
 * <p>
 * This class includes the following methods and constants:
 * <p>
 * 1. `ADDITIONAL_CASES`: A constant string containing additional characters such as punctuation marks,
 * symbols, and spaces that are included in both the English and Ukrainian alphabets.
 * <p>
 * 2. `ENGLISH_ALPHABET`: A constant string representing the English alphabet, including additional cases.
 * <p>
 * 3. `UKRAINIAN_ALPHABET`: A constant string representing the Ukrainian alphabet, including additional cases.
 * <p>
 * 4. `determineAlphabet(String text)`: Determines the alphabet based on the language detected in the input text.
 * It uses regular expressions to identify English and Ukrainian characters in the text. If the text contains
 * English characters, the English alphabet is returned; if it contains Ukrainian characters, the Ukrainian
 * alphabet is returned. If neither is detected, an IllegalArgumentException is thrown, indicating that
 * the language in the text is not supported.
 * <p>
 * <p>
 * Note: This class assumes that the input text may contain characters from either the English or Ukrainian
 * alphabets, and it does not handle cases where both are present simultaneously.
 */
package com.javarush.cipher;

import java.util.regex.Pattern;


public class AlphabetDeterminer {
    /**
     * A constant string containing additional characters such as punctuation marks,
     * symbols, and spaces that are included in both the English and Ukrainian alphabets.
     */
    private static final String ADDITIONAL_CASES = ".«»,\":!? /\\'";
    /**
     * A constant string representing the English alphabet, including additional cases.
     */
    private static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz" + ADDITIONAL_CASES;
    /**
     * A constant string representing the Ukrainian alphabet, including additional cases.
     */
    private static final String UKRAINIAN_ALPHABET = "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя" + ADDITIONAL_CASES;

    /**
     * Determines the alphabet based on the language detected in the input text.
     * It uses regular expressions to identify English and Ukrainian characters in the text.
     * If the text contains English characters, the English alphabet is returned; if it contains
     * Ukrainian characters, the Ukrainian alphabet is returned. If neither is detected,
     * an IllegalArgumentException is thrown, indicating that the language in the text is not supported.
     *
     * @param text The input text for language detection.
     * @return The determined alphabet based on the detected language.
     * @throws IllegalArgumentException If the language in the text is not supported.
     */

    public static String determineAlphabet(String text) {
        Pattern englishPattern = Pattern.compile("[a-zA-Z]");
        Pattern ukrainianPattern = Pattern.compile("[а-яА-ЯґҐєЄіІїЇ]");

        if (englishPattern.matcher(text).find()) {
            return ENGLISH_ALPHABET;
        } else if (ukrainianPattern.matcher(text).find()) {
            return UKRAINIAN_ALPHABET;
        } else {
            throw new IllegalArgumentException("Unsupported language in the text");
        }
    }

    public static String getAdditionalCases() {
        return ADDITIONAL_CASES;
    }

    public static String getEnglishAlphabet() {
        return ENGLISH_ALPHABET;
    }

    public static String getUkrainianAlphabet() {
        return UKRAINIAN_ALPHABET;
    }
}
