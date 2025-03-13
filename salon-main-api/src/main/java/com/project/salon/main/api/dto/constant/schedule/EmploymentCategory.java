package com.project.salon.main.api.dto.constant.schedule;

public enum EmploymentCategory {
    EMPOLYMENT("근무"),
    HOLIDAY("휴무"),
    VACATION("연차"),
    SICKLEAVE("병가"),
    OFFICIALVACATION("공가");

    private final String message;

    EmploymentCategory(String message) {
        this.message = message;
    }
}
