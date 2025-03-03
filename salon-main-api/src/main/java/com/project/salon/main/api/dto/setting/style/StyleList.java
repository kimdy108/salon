package com.project.salon.main.api.dto.setting.style;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StyleList {
    private UUID styleGuid;
    private String styleName;
    private String companyName;
    private String userName;
    private int styleDuration;
    private IsYesNo isActive;
    private IsYesNo isMiddleTime;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
