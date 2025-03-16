package com.project.salon.main.api.dto.setting.style;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StyleListAll {
    private UUID styleGuid;
    private String styleName;
    private int styleDuration;
}
