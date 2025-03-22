package com.project.salon.main.api.dto.schedule.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfo {
    UUID reservationGuid;
    String reservationYear;
    String reservationMonth;
    String reservationDay;
    String reservationHour;
    String reservationMinute;
    String styleName;
    int styleDuration;
    String clientName;
    String clientNumber;
    String descriptionNote;
}
