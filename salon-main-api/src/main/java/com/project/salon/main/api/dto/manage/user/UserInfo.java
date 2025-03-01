package com.project.salon.main.api.dto.manage.user;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String companyName;
    private UUID userGuid;
    private String userID;
    private String userName;
    private AdminRole userRole;
    private String userPhone;
    private String userEmail;
    private String descriptionNote;
}
