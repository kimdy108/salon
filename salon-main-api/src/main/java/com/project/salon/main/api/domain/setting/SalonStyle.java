package com.project.salon.main.api.domain.setting;

import com.project.salon.main.api.domain.common.SalonCommonBase;
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
@Table(name = "salon_style", indexes = {
        @Index(name = "idx_style_guid", columnList = "style_guid"),
        @Index(name = "idx_company_guid", columnList = "company_guid"),
        @Index(name = "idx_admin_guid", columnList = "admin_guid")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonStyle extends SalonCommonBase {
    @Comment("스타일 GUID")
    @Column(name = "style_guid", unique = true, length = 50, updatable = false, nullable = false)
    private UUID styleGuid;

    @Comment("스타일 이름")
    @Column(name = "style_name", length = 50, nullable = false)
    private String styleName;

    @Comment("사용자 seq")
    @Column(name = "admin_seq", length = 10, nullable = false)
    private Long adminSeq;

    @Comment("사용자 GUID")
    @Column(name = "admin_guid", length = 50, updatable = false, nullable = false)
    private UUID adminGuid;

    @Comment("스타일 상세")
    @Column(name = "style_detail", columnDefinition = "TEXT")
    private String styleDetail;

    @Comment("시술 시간")
    @Column(name = "style_duration", length = 10, nullable = false)
    private int styleDuration;

    @Comment("중간 예약 여부")
    @Column(name = "is_middle_time", columnDefinition = "ENUM('YES','NO') NOT NULL DEFAULT 'NO'")
    @Enumerated(EnumType.STRING)
    private IsYesNo isMiddleTime;

    @Comment("중간 시간")
    @Column(name = "middle_time", length = 10)
    private String middleTime;

    @Builder
    public SalonStyle (UUID styleGuid, String styleName, Long adminSeq, UUID adminGuid, String styleDetail, int styleDuration, IsYesNo isMiddleTime, String middleTime, IsYesNo isActive, LocalDateTime insertData, LocalDateTime updateData, String descriptionNote) {
        this.styleGuid = styleGuid;
        this.styleName = styleName;
        this.adminSeq = adminSeq;
        this.adminGuid = adminGuid;
        this.styleDetail = styleDetail;
        this.styleDuration = styleDuration;
        this.isMiddleTime = isMiddleTime;
        this.middleTime = middleTime;

        this.setIsActive(isActive);
        this.setInsertDate(insertData);
        this.setUpdateDate(updateData);
        this.setDescriptionNote(descriptionNote);
    }

    public void update(String styleName, String styleDetail, int styleDuration, IsYesNo isMiddleTime, String middleTime, LocalDateTime updateDate, String descriptionNote) {
        this.styleName = styleName;
        this.styleDetail = styleDetail;
        this.styleDuration = styleDuration;
        this.isMiddleTime = isMiddleTime;
        this.middleTime = middleTime;

        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
