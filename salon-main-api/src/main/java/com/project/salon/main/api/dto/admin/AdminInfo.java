package com.project.salon.main.api.dto.admin;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfo {
    private UUID adminGuid;
    private AdminRole adminRole;
    private String adminID;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
    private String descriptionNote;
}
