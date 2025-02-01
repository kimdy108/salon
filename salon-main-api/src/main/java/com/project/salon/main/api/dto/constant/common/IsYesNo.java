package com.project.salon.main.api.dto.constant.common;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum IsYesNo {
    YES("사용"),
    NO("정지");

    private final String message;

    IsYesNo(String message) {
        this.message = message;
    }
}
