package com.project.salon.main.api.dto.dashboard.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleByMonth {
    private int month;
    private int count;
}
