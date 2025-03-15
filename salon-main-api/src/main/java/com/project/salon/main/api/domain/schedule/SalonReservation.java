package com.project.salon.main.api.domain.schedule;

import com.project.salon.main.api.domain.common.SalonCommonBase;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
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
@Table(name = "salon_reservation", indexes = {
        @Index(name = "idx_reservation_guid", columnList = "reservation_guid"),
        @Index(name = "idx_admin_guid", columnList = "admin_guid")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonReservation extends SalonCommonBase {
    @Comment("예약 GUID")
    @Column(name = "reservation_guid", unique = true, length = 50, updatable = false, nullable = false)
    private UUID reservationGuid;

    @Comment("사용자 seq")
    @Column(name = "admin_seq", length = 10, nullable = false)
    private Long adminSeq;

    @Comment("사용자 GUID")
    @Column(name = "admin_guid", length = 50, updatable = false, nullable = false)
    private UUID adminGuid;

    @Comment("스타일 seq")
    @Column(name = "style_seq", length = 10, nullable = false)
    private Long styleSeq;

    @Comment("스타일 GUID")
    @Column(name = "style_guid", length = 50, updatable = false, nullable = false)
    private UUID styleGuid;

    @Comment("예약 년")
    @Column(name = "reservation_year", nullable = false, length = 10)
    private String reservationYear;

    @Comment("예약 월")
    @Column(name = "reservation_month", nullable = false, length = 10)
    private String reservationMonth;

    @Comment("예약 일")
    @Column(name = "reservation_day", nullable = false, length = 10)
    private String reservationDay;

    @Comment("예약 시")
    @Column(name = "reservation_hour", nullable = false, length = 10)
    private String reservationHour;

    @Comment("예약 분")
    @Column(name = "reservation_minute", nullable = false, length = 10)
    private String reservationMinute;

    @Comment("종료 시")
    @Column(name = "reservation_end_hour", nullable = false, length = 10)
    private String reservationEndHour;

    @Comment("종료 분")
    @Column(name = "reservation_end_minute", nullable = false, length = 10)
    private String reservationEndMinute;

    @Comment("예약자 이름")
    @Column(name = "client_name", nullable = false, length = 10)
    private String clientName;

    @Comment("예약자 번호")
    @Column(name = "client_number", nullable = false, length = 10)
    private String clientNumber;

    @Builder
    public SalonReservation (UUID reservationGuid, Long adminSeq, UUID adminGuid, Long styleSeq, UUID styleGuid, String reservationYear, String reservationMonth, String reservationDay, String reservationHour, String reservationMinute, String reservationEndHour, String reservationEndMinute, String clientName, String clientNumber, IsYesNo isActive, LocalDateTime insertDate, LocalDateTime updateDate, String descriptionNote) {
        this.reservationGuid = reservationGuid;
        this.adminSeq = adminSeq;
        this.adminGuid = adminGuid;
        this.styleSeq = styleSeq;
        this.styleGuid = styleGuid;
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationDay = reservationDay;
        this.reservationHour = reservationHour;
        this.reservationMinute = reservationMinute;
        this.reservationEndHour = reservationEndHour;
        this.reservationEndMinute = reservationEndMinute;
        this.clientName = clientName;
        this.clientNumber = clientNumber;

        this.setIsActive(isActive);
        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
