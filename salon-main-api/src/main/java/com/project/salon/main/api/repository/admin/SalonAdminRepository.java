package com.project.salon.main.api.repository.admin;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SalonAdminRepository extends JpaRepository<SalonAdmin, Long> {
    SalonAdmin findSalonAdminByAdminID(String adminID);

    SalonAdmin findSalonAdminByAdminIDAndCompanySeq(String adminID, Long companySeq);

    SalonAdmin findSalonAdminByAdminGuid(UUID adminGuid);

    @Query("update SalonAdmin set lastDate = :lastDate where adminGuid = :adminGuid")
    @Modifying
    @Transactional
    void updateLastDateByAdminGuid(UUID adminGuid, LocalDateTime lastDate);

    @Query("update SalonAdmin set isActive = :isActive, updateDate = :updateDate where adminGuid = :adminGuid")
    @Modifying
    @Transactional
    void updateSalonAdminByIsActiveStatus(UUID adminGuid, IsYesNo isActive, LocalDateTime updateDate);
}
