package com.project.salon.main.api.controller.common;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.component.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final LoggerService loggerService;

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseMsg> handleRuntimeException(final RuntimeException e) {
        loggerService.writeLogger("error", "Runtime Error : " + e.getMessage());
        e.printStackTrace();

        return ResponseMsg.errorResponse(e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ResponseMsg> handleUsernameNotFoundException(final UsernameNotFoundException e) {
        loggerService.writeLogger("error", "Username not found : " + e.getMessage());

        return ResponseMsg.authFailResponse(e.getMessage());
    }
}
