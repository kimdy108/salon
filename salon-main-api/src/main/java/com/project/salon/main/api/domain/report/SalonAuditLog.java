package com.project.salon.main.api.domain.report;

import com.project.salon.main.api.domain.common.SalonCommonLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salon_audit_log", indexes = {
        @Index(name = "idx_user_guid", columnList = "user_guid"),
        @Index(name = "idx_controller_type", columnList = "controller_type")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonAuditLog extends SalonCommonLog {
    @Comment("감사로그 GUID")
    @Column(name = "audit_guid", length = 50, unique = true, nullable = false)
    private UUID auditGuid;

    @Comment("컨트롤러 타입")
    @Column(name = "controller_type", length = 50)
    private String controllerType;

    @Comment("컨트롤러 카테고리")
    @Column(name = "controller_category", length = 50)
    private String controllerCategory;

    @Comment("사용자 GUID")
    @Column(name = "admin_guid", length = 50, nullable = false)
    private UUID adminGuid;

    @Comment("상세")
    @Column(name = "audit_detail", columnDefinition = "TEXT")
    private String auditDetail;

    @Builder
    public SalonAuditLog (UUID auditGuid, String controllerType, String controllerCategory, UUID adminGuid, String auditDetail, LocalDateTime insertDate, Long insertTimestamp) {
        this.auditGuid = auditGuid;
        this.controllerType = controllerType;
        this.controllerCategory = controllerCategory;
        this.adminGuid = adminGuid;
        this.auditDetail = auditDetail;

        this.setInsertDate(insertDate);
        this.setInsertTimestamp(insertTimestamp);
    }
}
