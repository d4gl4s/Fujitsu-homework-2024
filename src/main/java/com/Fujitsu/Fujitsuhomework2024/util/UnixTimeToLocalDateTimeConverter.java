package com.Fujitsu.Fujitsuhomework2024.util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UnixTimeToLocalDateTimeConverter {

    /**
     * Converts a UNIX timestamp to a {@code LocalDateTime} object.
     *
     * @param unixTimestamp The UNIX timestamp to convert.
     * @return The {@code LocalDateTime} representation of the UNIX timestamp.
     */
    public static LocalDateTime convertUnixTimestamp(long unixTimestamp) {
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
