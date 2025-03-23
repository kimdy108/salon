package com.project.salon.main.api.domain.setting;

import com.project.salon.main.api.domain.common.SalonCommonBase;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.system.HoursCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salon_system", indexes = {
        @Index(name = "idx_company_guid", columnList = "company_guid")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonSystem extends SalonCommonBase {
    @Comment("고객사 SEQ")
    @Column(name = "company_seq", length = 10, nullable = false)
    private Long companySeq;

    @Comment("고객사 GUID")
    @Column(name = "company_guid", length = 50, updatable = false, nullable = false)
    private UUID companyGuid;

    @Comment("요일")
    @Column(name = "day_of_week", columnDefinition = "ENUM('SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY') NOT NULL DEFAULT 'SUNDAY'")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Comment("근무 / 휴무")
    @Column(name = "hours_category", columnDefinition = "ENUM('EMPLOYMENT','HOLIDAY') NOT NULL DEFAULT 'HOLIDAY'")
    @Enumerated(EnumType.STRING)
    private HoursCategory hoursCategory;

    @Comment("영업 시작 시간")
    @Column(name = "start_time_hour", nullable = false, length = 10)
    private String startTimeHour;

    @Comment("영업 시간 분")
    @Column(name = "start_time_minute", nullable = false, length = 10)
    private String startTimeMinute;

    @Comment("영업 종료 시간")
    @Column(name = "end_time_hour", nullable = false, length = 10)
    private String endTimeHour;

    @Comment("영업 종료 분")
    @Column(name = "end_time_minute", nullable = false, length = 10)
    private String endTimeMinute;

    @Builder
    public SalonSystem (Long companySeq, UUID companyGuid, DayOfWeek dayOfWeek, HoursCategory hoursCategory, String startTimeHour, String startTimeMinute, String endTimeHour, String endTimeMinute, LocalDateTime insertDate, LocalDateTime updateDate, IsYesNo isActive, String descriptionNote) {
        this.companySeq = companySeq;
        this.companyGuid = companyGuid;
        this.dayOfWeek = dayOfWeek;
        this.hoursCategory = hoursCategory;
        this.startTimeHour = startTimeHour;
        this.startTimeMinute = startTimeMinute;
        this.endTimeHour = endTimeHour;
        this.endTimeMinute = endTimeMinute;

        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setIsActive(isActive);
        this.setDescriptionNote(descriptionNote);
    }
}
