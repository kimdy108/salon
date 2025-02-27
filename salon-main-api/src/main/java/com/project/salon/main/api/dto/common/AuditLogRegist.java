package com.project.salon.main.api.dto.common;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuditLogRegist {
    private String accessToken;
    private String controllerCategory;
    private String controllerType;
    private Object auditDetail;

    @Builder
    public AuditLogRegist(String accessToken, String controllerCategory, String controllerType, Object auditDetail) {
        this.accessToken = accessToken;
        this.controllerCategory = controllerCategory;
        this.controllerType = controllerType;
        this.auditDetail = auditDetail;
    }
}
