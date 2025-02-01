package com.project.salon.main.api.domain.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.salon.main.api.domain.common.SalonCommonBase;
import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salon_admin", indexes = {
        @Index(name = "idx_admin_id", columnList = "admin_id"),
        @Index(name = "idx_admin_guid", columnList = "admin_guid"),
        @Index(name = "idx_admin_name", columnList = "admin_name"),
        @Index(name = "idx_company_guid", columnList = "company_guid")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonAdmin extends SalonCommonBase {
    @Comment("사용자 UUID")
    @Column(name = "admin_guid", unique = true, length = 50, updatable = false, nullable = false)
    private UUID adminGuid;

    @Comment("사용자 아이디")
    @Column(name = "admin_id", unique = true, length = 50, nullable = false)
    private String adminID;

    @Comment("사용자 비밀번호")
    @Column(name = "admin_password", length = 100, nullable = false)
    private String adminPassword;

    @Comment("고객사 UUID")
    @Column(name = "company_guid", length = 50, nullable = false)
    private UUID companyGuid;

    @Comment("고객사 seq")
    @Column(name = "company_seq", length = 10, nullable = false)
    private Long companySeq;

    @Comment("사용자 이름")
    @Column(name = "admin_name", length = 100, nullable = false)
    private String adminName;

    @Comment("사용자 전화번호")
    @Column(name = "admin_phone", length = 200, nullable = false)
    private String adminPhone;

    @Comment("사용자 Email")
    @Column(name = "admin_email", length = 200, nullable = false)
    private String adminEmail;

    @Comment("사용자 권한")
    @Enumerated(EnumType.STRING)
    @Column(name = "admin_role", columnDefinition="ENUM('MASTER','ADMIN','MANAGER','MEMBER') NOT NULL DEFAULT 'MEMBER'")
    private AdminRole adminRole;

    @Comment("사용자 형식 : system 생성 - 사용자 생성")
    @Column(name = "admin_type", columnDefinition = "ENUM('SYSTEM','NORMAL') NOT NULL DEFAULT 'SYSTEM'")
    private String adminType;

    @Comment("마지막 접근날짜 (로그인 등)")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "last_date", columnDefinition="DATETIME")
    private LocalDateTime lastDate;

    @Builder
    public SalonAdmin (UUID adminGuid, String adminID, String adminPassword, UUID companyGuid, Long companySeq, String adminName, String adminPhone, String adminEmail, AdminRole adminRole, String adminType, LocalDateTime lastDate, IsYesNo isActive, LocalDateTime insertDate, LocalDateTime updateDate, String descriptionNote) {
        this.adminGuid = adminGuid;
        this.adminID = adminID;
        this.adminPassword = adminPassword;
        this.companyGuid = companyGuid;
        this.companySeq = companySeq;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminEmail = adminEmail;
        this.adminRole = adminRole;
        this.adminType = adminType;
        this.lastDate = lastDate;

        this.setIsActive(isActive);
        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
