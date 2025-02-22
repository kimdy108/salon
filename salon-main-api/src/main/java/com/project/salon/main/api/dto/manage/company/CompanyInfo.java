package com.project.salon.main.api.dto.manage.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInfo {
    private UUID companyGuid;
    private String companyNumber;
    private String companyName;
    private String companyAddress;
    private String managerName;
    private String managerPhone;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    private String descriptionNote;
}
