package com.project.salon.main.api.dto.setting.style;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StyleUpdate {
    private UUID styleGuid;
    private String styleName;
    private String styleDetail;
    private int styleDuration;
    private IsYesNo isMiddleTime;
    private List<String> middleTime;
    private String descriptionNote;
}
