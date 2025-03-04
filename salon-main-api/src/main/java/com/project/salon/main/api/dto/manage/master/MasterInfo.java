package com.project.salon.main.api.dto.manage.master;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterInfo {
    private String companyName;
    private UUID masterGuid;
    private String masterID;
    private String masterName;
    private AdminRole masterRole;
    private String masterPhone;
    private String masterEmail;
    private String descriptionNote;
}
