package be.unamur.fpgen.utils;

public class StringUtil {
    public static String toLowerCaseIfNotNull(String s) {
        return s == null ? null : s.toLowerCase();
    }
}
