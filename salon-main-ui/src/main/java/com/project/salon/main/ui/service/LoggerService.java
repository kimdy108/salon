package com.project.salon.main.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class LoggerService {
    public void writeLogger(String type, String message) {
        String nowdate = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
        switch (type.toUpperCase()) {
            case "TRACE" -> log.trace(nowdate + message);
            case "DEBUG" -> log.debug(nowdate + message);
            case "INFO" -> log.info(nowdate + message);
            case "WARN" -> log.warn(nowdate + message);
            case "ERROR" -> log.error(nowdate + message);
        }
    }
}
