package com.project.salon.main.api.dto.constant.system;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum HoursCategory {
    EMPLOYMENT("근무"),
    HOLIDAY("휴무");

    private final String message;

    HoursCategory(String message) {
        this.message = message;
    }
}
