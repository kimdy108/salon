package com.project.salon.main.api.dto.manage.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListAll {
    private UUID companyGuid;
    private String companyName;
    private String managerName;
}
