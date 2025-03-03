package com.project.salon.main.api.dto.report.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogList {
    private UUID auditGuid;
    private String controllerType;
    private String controllerCategory;
    private UUID userGuid;
    private String auditDetail;
    private LocalDateTime insertDate;

    private String userID;
    private String userName;
    private String companyNumber;
    private String companyName;
}
