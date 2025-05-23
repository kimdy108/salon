package com.project.salon.main.api.config;

import com.project.salon.main.api.service.component.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent> {
    private final LoggerService loggerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        loggerService.writeLogger("info", "\n" +
                "███████  █████  ██       ██████  ███    ██     ███    ███  █████  ██ ███    ██      █████  ██████  ██  \n" +
                "██      ██   ██ ██      ██    ██ ████   ██     ████  ████ ██   ██ ██ ████   ██     ██   ██ ██   ██ ██  \n" +
                "███████ ███████ ██      ██    ██ ██ ██  ██     ██ ████ ██ ███████ ██ ██ ██  ██     ███████ ██████  ██  \n" +
                "     ██ ██   ██ ██      ██    ██ ██  ██ ██     ██  ██  ██ ██   ██ ██ ██  ██ ██     ██   ██ ██      ██  \n" +
                "███████ ██   ██ ███████  ██████  ██   ████     ██      ██ ██   ██ ██ ██   ████     ██   ██ ██      ██  \n" );
    }
}
