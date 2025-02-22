package com.project.salon.main.api.dto.manage.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdate {
    private UUID companyGuid;
    private String companyName;
    private String companyAddress;
    private String managerName;
    private String managerPhone;
    private String descriptionNote;
}
