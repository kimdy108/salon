package com.project.salon.main.api.repository.setting;

import com.project.salon.main.api.domain.setting.SalonStyle;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SalonStyleRepository extends JpaRepository<SalonStyle, Long> {
    SalonStyle findSalonStyleByStyleGuid(UUID styleGuid);

    @Query("update SalonStyle set isActive = :isActive, updateDate = :updateDate where styleGuid = :styleGuid")
    @Modifying
    @Transactional
    void updateSalonStyleByActiveStatus(UUID styleGuid, IsYesNo isActive, LocalDateTime updateDate);
}
