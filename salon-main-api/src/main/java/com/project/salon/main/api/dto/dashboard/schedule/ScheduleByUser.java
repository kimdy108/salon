package com.project.salon.main.api.dto.dashboard.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleByUser {
    private String companyName;
    private String userName;
    private Long scheduleCount;
}
