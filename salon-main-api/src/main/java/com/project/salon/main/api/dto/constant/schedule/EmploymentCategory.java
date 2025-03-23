package com.project.salon.main.api.dto.constant.schedule;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum EmploymentCategory {
    EMPLOYMENT("근무"),
    HOLIDAY("휴무"),
    VACATION("연차"),
    SICKLEAVE("병가"),
    OFFICIALVACATION("공가");

    private final String message;

    EmploymentCategory(String message) {
        this.message = message;
    }
}
