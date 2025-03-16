package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.schedule.SalonReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface SalonReservationRepository extends JpaRepository<SalonReservation, Long> {
    SalonReservation findSalonReservationByReservationGuid(UUID reservationGuid);
    SalonReservation findSalonReservationByReservationYearAndReservationMonthAndReservationDayAndReservationHourAndReservationMinute(String reservationYear, String reservationMonth, String reservationDay, String reservationHour, String reservationMinute);

    @Query("delete from SalonReservation where reservationPartnerGuid = :reservationPartnerGuid")
    @Modifying
    @Transactional
    void deleteByReservationPartnerGuid(UUID reservationPartnerGuid);
}
