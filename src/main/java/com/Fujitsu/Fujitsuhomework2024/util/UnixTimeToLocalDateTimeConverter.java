package com.Fujitsu.Fujitsuhomework2024.util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
public class UnixTimeToLocalDateTimeConverter {
    public static LocalDateTime convertUnixTimestamp(long unixTimestamp) {
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
