package com.project.salon.main.api.dto.manage.user;

import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActive {
    private UUID userGuid;
    private IsYesNo isActive;
}
