package com.project.salon.main.api.dto.admin;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AdminAuth {
    private final String accessToken;
    private final String refreshToken;
    private final String userName;
    private final UUID userGuid;
    private final AdminRole userRole;
    private final UUID companyGuid;
    private final UUID sessionGuid;

    @Builder
    public AdminAuth(String accessToken, String refreshToken, String userName, UUID userGuid, AdminRole userRole, UUID companyGuid, UUID sessionGuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userName = userName;
        this.userGuid = userGuid;
        this.userRole = userRole;
        this.companyGuid = companyGuid;
        this.sessionGuid = sessionGuid;
    }
}
