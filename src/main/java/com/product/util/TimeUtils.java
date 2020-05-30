package com.product.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtils {

    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    private TimeUtils() {
    }

    public static LocalDateTime getUtcTime() {
        return ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

}