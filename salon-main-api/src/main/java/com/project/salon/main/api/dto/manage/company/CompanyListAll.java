package com.project.salon.main.api.dto.manage.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListAll {
    private Long companySeq;
    private String companyGuid;
    private String companyName;
    private String managerName;
}
