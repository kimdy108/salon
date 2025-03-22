package com.project.salon.main.api.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdatePassword {
    private UUID adminGuid;
    private String oldAdminPassword;
    private String newAdminPassword;
}
