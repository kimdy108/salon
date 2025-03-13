package com.project.salon.main.api.domain.schedule;

import com.project.salon.main.api.domain.common.SalonCommonBase;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.schedule.EmploymentCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salon_employment", indexes = {
        @Index(name = "idx_employment_guid", columnList = "employment_guid"),
        @Index(name = "idx_admin_guid", columnList = "admin_guid")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonEmployment extends SalonCommonBase {
    @Comment("근태 GUID")
    @Column(name = "employment_guid", unique = true, length = 50, updatable = false, nullable = false)
    private UUID employmentGuid;

    @Comment("사용자 seq")
    @Column(name = "admin_seq", length = 10, nullable = false)
    private Long adminSeq;

    @Comment("사용자 GUID")
    @Column(name = "admin_guid", length = 50, updatable = false, nullable = false)
    private UUID adminGuid;

    @Comment("근태 날짜")
    @Column(name = "employment_date", nullable = false, length = 10)
    private String employmentDate;

    @Comment("근태 종류")
    @Column(name = "employment_category", columnDefinition = "ENUM('EMPOLYMENT','HOLIDAY','VACATION','SICKLEAVE','OFFICIALVACATION') NOT NULL DEFAULT 'EMPOLYMENT'")
    @Enumerated(EnumType.STRING)
    private EmploymentCategory employmentCategory;

    @Builder
    public SalonEmployment (UUID employmentGuid, Long adminSeq, UUID adminGuid, String employmentDate, EmploymentCategory employmentCategory, IsYesNo isActive, LocalDateTime insertData, LocalDateTime updateData, String descriptionNote) {
        this.employmentGuid = employmentGuid;
        this.adminSeq = adminSeq;
        this.adminGuid = adminGuid;
        this.employmentDate = employmentDate;
        this.employmentCategory = employmentCategory;

        this.setIsActive(isActive);
        this.setInsertDate(insertData);
        this.setUpdateDate(updateData);
        this.setDescriptionNote(descriptionNote);
    }
}
