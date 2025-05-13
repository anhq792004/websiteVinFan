package com.example.datn.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    // Format LocalDate -> String
    public static String formatDateToStr(LocalDate date) {
        return (date != null) ? date.format(DATE_FORMATTER) : null;
    }

    // Format LocalTime -> String
    public static String formatTimeToStr(LocalTime time) {
        return (time != null) ? time.format(TIME_FORMATTER) : null;
    }

    // Format LocalDateTime -> String
    public static String formatDateTimeToStr(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.format(DATETIME_FORMATTER) : null;
    }

    // Parse String -> LocalDate
    public static LocalDate parseDate(String dateStr) {
        return (dateStr != null && !dateStr.isEmpty()) ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
    }

    // Parse String -> LocalTime
    public static LocalTime parseTime(String timeStr) {
        return (timeStr != null && !timeStr.isEmpty()) ? LocalTime.parse(timeStr, TIME_FORMATTER) : null;
    }

    // Parse String -> LocalDateTime
    public static LocalDateTime parseDateTime(String dateStr, String timeStr) {
        if (dateStr == null || timeStr == null || dateStr.isEmpty() || timeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.of(parseDate(dateStr), parseTime(timeStr));
    }
}
