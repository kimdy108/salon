package com.project.salon.main.api.dto.setting.style;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StyleActive {
    private UUID styleGuid;
    private IsYesNo isActive;
}
