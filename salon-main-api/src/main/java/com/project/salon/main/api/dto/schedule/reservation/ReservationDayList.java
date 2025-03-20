package com.project.salon.main.api.dto.schedule.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDayList {
    private UUID reservationGuid;
    private String companyName;
    private String userName;
    private String styleName;
    private String clientName;
    private String reservationYear;
    private String reservationMonth;
    private String reservationDay;
    private String reservationHour;
    private String reservationMinute;
    private String reservationEndHour;
    private String reservationEndMinute;
}
