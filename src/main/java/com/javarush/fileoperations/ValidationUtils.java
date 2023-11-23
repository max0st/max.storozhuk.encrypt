package com.javarush.fileoperations;

public class ValidationUtils {

    private ValidationUtils() {
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
