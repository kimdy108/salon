package com.project.salon.main.api.dto.schedule.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRegist {
    private UUID companyGuid;
    private UUID userGuid;
    private UUID styleGuid;
    private String reservationDate;
    private String clientName;
    private String clientNumber;
    private String descriptionNote;
}
