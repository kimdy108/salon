package com.project.salon.main.api.dto.manage.company;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyActive {
    private UUID companyGuid;
    private IsYesNo isActive;
}
