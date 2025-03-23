package com.project.salon.main.api.dto.setting.system;

import com.project.salon.main.api.dto.constant.system.HoursCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoursInfo {
    private UUID companyGuid;
    private DayOfWeek dayOfWeek;
    private HoursCategory hoursCategory;
    private String startTimeHour;
    private String startTimeMinute;
    private String endTimeHour;
    private String endTimeMinute;
}
