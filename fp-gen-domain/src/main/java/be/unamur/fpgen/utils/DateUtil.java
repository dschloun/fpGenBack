package be.unamur.fpgen.utils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This class is used to convert dates from one format to another.
 */
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

    public static String convertOffsetDateTimeMillisToString(final OffsetDateTime date) {
        // Utilisation du pattern avec secondes et fractions de seconde
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss.SSS");
        return date.format(formatter);
    }

    /**
     * Converts a LocalDate to OffsetDateTime using the ZoneId for Brussels.
     *
     * @param localDate the LocalDate to be converted
     * @return the corresponding OffsetDateTime for Brussels
     */
    public static OffsetDateTime convertLocalDateToOffsetDateTime(LocalDate localDate) {
        if (Objects.isNull(localDate)){
            return null;
        }
        ZoneId brusselsZoneId = ZoneId.of("Europe/Brussels");
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(brusselsZoneId);
        return zonedDateTime.toOffsetDateTime();
    }
    public static OffsetDateTime ifNullReturnOldDate(OffsetDateTime date) {
        return date != null ? date : OffsetDateTime.now().minusYears(5);
    }
    public static OffsetDateTime ifNullReturnTomorrow(OffsetDateTime date) {
        return date == null ? OffsetDateTime.now().plusDays(1) : date;
    }
}
