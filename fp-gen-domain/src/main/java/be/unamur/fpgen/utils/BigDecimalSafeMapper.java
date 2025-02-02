package be.unamur.fpgen.utils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This class is used to map a BigDecimal to a Double and vice versa.
 * It is used to avoid null pointer exception when mapping a BigDecimal to a Double.
 */
public class BigDecimalSafeMapper {

    public static BigDecimal map(final Double value) {
        return Objects.isNull(value) ? null : BigDecimal.valueOf(value);
    }

    public static Double map(final BigDecimal value) {
        return Objects.isNull(value) ? null : value.doubleValue();
    }
}
