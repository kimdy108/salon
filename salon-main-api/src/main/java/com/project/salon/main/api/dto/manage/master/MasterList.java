package com.project.salon.main.api.dto.manage.master;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterList {
    private UUID masterGuid;
    private String masterID;
    private String masterName;
    private AdminRole masterRole;
    private IsYesNo isActive;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
