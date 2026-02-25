package com.pitercoding.springboot2.essentials.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public String formatLocalDateTimeToDateBaseStyle(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(localDateTime);
    }
}
