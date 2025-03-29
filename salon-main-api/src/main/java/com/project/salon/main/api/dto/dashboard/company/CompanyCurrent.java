package com.project.salon.main.api.dto.dashboard.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCurrent {
    private int allCompany;
    private int addCompany;
    private int activeCompany;
}
