package com.project.salon.main.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallSalon {
    private String authKey;
    private String adminID;
    private String adminPassword;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
}
