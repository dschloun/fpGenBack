package be.unamur.fpgen.utils;

/**
 * Utility class for string operations.
 */
public class StringUtil {
    public static String toLowerCaseIfNotNull(String s) {
        return s == null ? null : s.toLowerCase();
    }
}
