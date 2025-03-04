package com.project.salon.main.api.dto.manage.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterUpdatePassword {
    private UUID masterGuid;
    private String masterPassword;
}
