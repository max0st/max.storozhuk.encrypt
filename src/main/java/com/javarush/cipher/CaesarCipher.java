/**
 * The `CaesarCipher` class provides methods for encrypting and decrypting text using the Caesar cipher.
 * The Caesar cipher is a substitution cipher where each letter in the plaintext is shifted a certain
 * number of places down or up the alphabet.
 * <p>
 * This class uses a specified key for encryption and decryption. The key represents the number of positions
 * each letter in the alphabet should be shifted.
 * <p>
 * The class includes the following methods:
 * <p>
 * 1. `encrypt(String text, int key)`: Encrypts the input text using the Caesar cipher with the specified key.
 * ё
 * 2. `decrypt(String text, int key)`: Decrypts the input text using the Caesar cipher with the specified key.
 * It is essentially an encryption with the negation of the key.
 * <p>
 * 3. `encryptChar(char ch, String alphabet, int key)`: Private helper method that performs the encryption of
 * a single character. It takes into account the case of the character and ensures that non-alphabetic
 * characters remain unchanged.
 * <p>
 * Note: The `AlphabetDeterminer` class is assumed to provide the alphabet based on the input text.
 * The alphabet is used for determining which characters should be encrypted.
 */
package com.javarush.cipher;

public class CaesarCipher {
    /**
     * Encrypts the input text using the Caesar cipher with the specified key.
     *
     * @param text The plaintext to be encrypted.
     * @param key  The key representing the shift in the alphabet.
     * @return The encrypted text.
     */
    public static String encrypt(String text, int key) {
        String alphabet = AlphabetDeterminer.determineAlphabet(text);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (alphabet.contains(String.valueOf(Character.toLowerCase(ch)))) {
                char encryptedChar = encryptChar(ch, alphabet, key);
                result.append(encryptedChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    /**
     * Decrypts the input text using the Caesar cipher with the specified key.
     *
     * @param text The ciphertext to be decrypted.
     * @param key  The key representing the shift in the alphabet.
     * @return The decrypted text.
     */
    public static String decrypt(String text, int key) {
        return encrypt(text, -key);
    }

    /**
     * Private helper method that performs the encryption of a single character.
     * It takes into account the case of the character and ensures that non-alphabetic
     * characters remain unchanged.
     *
     * @param ch       The character to be encrypted.
     * @param alphabet The alphabet used for encryption.
     * @param key      The key representing the shift in the alphabet.
     * @return The encrypted character.
     */
    private static char encryptChar(char ch, String alphabet, int key) {
        char lowerCh = Character.toLowerCase(ch);
        int index = alphabet.indexOf(lowerCh);
        int encryptedIndex = (index + key) % alphabet.length();

        if (encryptedIndex < 0) {
            encryptedIndex += alphabet.length();
        }

        return (Character.isUpperCase(ch)) ?
                Character.toUpperCase(alphabet.charAt(encryptedIndex)) :
                alphabet.charAt(encryptedIndex);
    }
}

