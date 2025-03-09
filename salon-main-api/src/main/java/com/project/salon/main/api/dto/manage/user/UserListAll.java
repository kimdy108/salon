package com.project.salon.main.api.dto.manage.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListAll {
    private UUID userGuid;
    private String userName;
    private String companyName;
}
