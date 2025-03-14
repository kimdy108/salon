package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.schedule.SalonEmployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SalonEmploymentRepository extends JpaRepository<SalonEmployment, Long> {
    SalonEmployment findSalonEmploymentByEmploymentGuid(UUID employmentGuid);
    SalonEmployment findSalonEmploymentByAdminSeqAndEmploymentYearAndEmploymentMonthAndEmploymentDay(Long adminSeq, String employmentYear, String employmentMonth, String employmentDay);
}
