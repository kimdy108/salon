package com.project.salon.main.api.dto.manage.user;

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
public class UserList {
    private UUID userGuid;
    private String userID;
    private String userName;
    private String companyName;
    private AdminRole userRole;
    private IsYesNo isActive;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
