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

    @Comment("근태 년")
    @Column(name = "employment_year", nullable = false, length = 10)
    private String employmentYear;

    @Comment("근태 월")
    @Column(name = "employment_month", nullable = false, length = 10)
    private String employmentMonth;

    @Comment("근태 일")
    @Column(name = "employment_day", nullable = false, length = 10)
    private String employmentDay;

    @Comment("근태 종류")
    @Column(name = "employment_category", columnDefinition = "ENUM('EMPLOYMENT','HOLIDAY','VACATION','SICKLEAVE','OFFICIALVACATION') NOT NULL DEFAULT 'EMPLOYMENT'")
    @Enumerated(EnumType.STRING)
    private EmploymentCategory employmentCategory;

    @Builder
    public SalonEmployment (UUID employmentGuid, Long adminSeq, UUID adminGuid, String employmentYear, String employmentMonth, String employmentDay, EmploymentCategory employmentCategory, IsYesNo isActive, LocalDateTime insertData, LocalDateTime updateData, String descriptionNote) {
        this.employmentGuid = employmentGuid;
        this.adminSeq = adminSeq;
        this.adminGuid = adminGuid;
        this.employmentYear = employmentYear;
        this.employmentMonth = employmentMonth;
        this.employmentDay = employmentDay;
        this.employmentCategory = employmentCategory;

        this.setIsActive(isActive);
        this.setInsertDate(insertData);
        this.setUpdateDate(updateData);
        this.setDescriptionNote(descriptionNote);
    }

    public void update (EmploymentCategory employmentCategory, LocalDateTime updateDate, String descriptionNote) {
        this.employmentCategory = employmentCategory;

        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
