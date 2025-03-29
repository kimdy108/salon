package com.project.salon.main.api.dto.dashboard.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyByMonth {
    private int month;
    private int count;
}
