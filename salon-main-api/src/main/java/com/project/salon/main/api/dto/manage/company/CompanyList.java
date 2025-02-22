package com.project.salon.main.api.dto.manage.company;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyList {
    private UUID companyGuid;
    private String companyName;
    private String managerName;
    private String managerPhone;
    private IsYesNo isActive;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
