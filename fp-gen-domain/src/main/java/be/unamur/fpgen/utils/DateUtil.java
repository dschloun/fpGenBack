package be.unamur.fpgen.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    /**
     * @return a string representing the date in the format YYYY/MM/DD_HH:MM
     * @overview: convert an OffsetDateTime format in a string YYYY/MM/DD_HH:MM
     * @requires date != null
     */
    public static String convertOffsetDateTimeToString(final OffsetDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm"));
    }
}
