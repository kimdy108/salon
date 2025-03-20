package com.project.salon.main.api.dto.schedule.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationMonthList {
    private String userName;
    private Long reservationCount;
    private String reservationYear;
    private String reservationMonth;
    private String reservationDay;
}
