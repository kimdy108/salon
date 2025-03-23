package com.project.salon.main.api.repository.setting;

import com.project.salon.main.api.domain.setting.SalonSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.UUID;

@Repository
public interface SalonSystemRepository extends JpaRepository<SalonSystem, Long> {
    SalonSystem findSalonSystemByCompanyGuidAndDayOfWeek(UUID companyGuid, DayOfWeek dayOfWeek);
}
