package com.project.salon.main.api.dto.manage.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePassword {
    private UUID userGuid;
    private String userPassword;
}
