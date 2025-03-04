package com.project.salon.main.api.dto.manage.master;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterUpdate {
    private UUID masterGuid;
    private String masterName;
    private String masterPhone;
    private String masterEmail;
    private String descriptionNote;
}
