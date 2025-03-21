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
    String userName;
    String styleName;
    String clientName;
    String clientNumber;
}
