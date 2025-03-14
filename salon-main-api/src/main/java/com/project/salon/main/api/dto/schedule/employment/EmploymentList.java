package com.project.salon.main.api.dto.schedule.employment;

import com.project.salon.main.api.dto.constant.schedule.EmploymentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentList {
    private UUID employmentGuid;
    private String companyName;
    private String userName;
    private String employmentYear;
    private String employmentMonth;
    private String employmentDay;
    private EmploymentCategory employmentCategory;
}
