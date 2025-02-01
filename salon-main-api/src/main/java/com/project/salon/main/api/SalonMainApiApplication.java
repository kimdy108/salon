package com.project.salon.main.api;

import com.project.salon.main.api.service.component.LoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SalonMainApiApplication {
    private final LoggerService loggerService;

    public static void main(String[] args) {
        SpringApplication.run(SalonMainApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void whenReady() {
        loggerService.writeLogger("info", "\n" +
                "                                                                         \n" +
                "  ▗▄▄▖ ▗▄▖ ▗▖    ▗▄▖ ▗▖  ▗▖    ▗▖  ▗▖ ▗▄▖ ▗▄▄▄▖▗▖  ▗▖     ▗▄▖ ▗▄▄▖▗▄▄▄▖  \n" +
                " ▐▌   ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▛▚▖▐▌    ▐▛▚▞▜▌▐▌ ▐▌  █  ▐▛▚▖▐▌    ▐▌ ▐▌▐▌ ▐▌ █    \n" +
                "  ▝▀▚▖▐▛▀▜▌▐▌   ▐▌ ▐▌▐▌ ▝▜▌    ▐▌  ▐▌▐▛▀▜▌  █  ▐▌ ▝▜▌    ▐▛▀▜▌▐▛▀▘  █    \n" +
                " ▗▄▄▞▘▐▌ ▐▌▐▙▄▄▖▝▚▄▞▘▐▌  ▐▌    ▐▌  ▐▌▐▌ ▐▌▗▄█▄▖▐▌  ▐▌    ▐▌ ▐▌▐▌  ▗▄█▄▖  \n" +
                "                                                                         \n" );
    }
}
