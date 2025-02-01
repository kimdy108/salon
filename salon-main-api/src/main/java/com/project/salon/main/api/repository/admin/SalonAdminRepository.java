package com.project.salon.main.api.repository.admin;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SalonAdminRepository extends JpaRepository<SalonAdmin, Long> {
    SalonAdmin findSalonAdminByAdminID(String adminID);

    SalonAdmin findSalonAdminByAdminIDAndCompanySeq(String adminID, Long companySeq);

    @Query("update SalonAdmin set lastDate = :lastDate where adminGuid = :adminGuid")
    @Modifying
    @Transactional
    void updateLastDateByAdminGuid(UUID adminGuid, LocalDateTime lastDate);
}
