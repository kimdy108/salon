package com.project.salon.main.api.dto.dashboard.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleByDay {
    private String companyName;
    private String userName;
    private int scheduleCount;
}
