package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.schedule.SalonReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonReservationRepository extends JpaRepository<SalonReservation, Long> {
}
