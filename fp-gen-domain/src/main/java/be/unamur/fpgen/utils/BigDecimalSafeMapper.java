package be.unamur.fpgen.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalSafeMapper {

    public static BigDecimal map(final Double value) {
        return Objects.isNull(value) ? null : BigDecimal.valueOf(value);
    }

    public static Double map(final BigDecimal value) {
        return Objects.isNull(value) ? null : value.doubleValue();
    }
}
