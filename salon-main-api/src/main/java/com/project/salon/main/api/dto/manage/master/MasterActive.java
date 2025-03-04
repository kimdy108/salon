package com.project.salon.main.api.dto.manage.master;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterActive {
    private UUID masterGuid;
    private IsYesNo isActive;
}
