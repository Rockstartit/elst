package edu.kit.elst.rest_api;

import java.math.BigDecimal;
import java.time.Duration;

public class UtilMapper {
    public static BigDecimal mapToBigDecimal(long value) {
        return BigDecimal.valueOf(value);
    }

    public static BigDecimal mapToBigDecimal(int value) {
        return BigDecimal.valueOf(value);
    }

    public static BigDecimal mapToBigDecimal(Duration duration) {
        return BigDecimal.valueOf(duration.toMinutes());
    }

    public static Duration mapToDuration(BigDecimal duration) {
        return Duration.ofMinutes(duration.longValue());
    }
}
