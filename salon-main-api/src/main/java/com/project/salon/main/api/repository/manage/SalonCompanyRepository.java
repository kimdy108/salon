package com.project.salon.main.api.repository.manage;

import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SalonCompanyRepository extends JpaRepository<SalonCompany, Long> {
    SalonCompany findSalonCompanyByCompanyGuid(UUID companyGuid);
    SalonCompany findSalonCompanyByCompanyNumber(String companyNumber);

    @Query("update SalonCompany set isActive = :isActive, updateDate = :updateDate where companyGuid = :companyGuid")
    @Modifying
    @Transactional
    void updateSalonCompanyByActiveStatus(UUID companyGuid, IsYesNo isActive, LocalDateTime updateDate);
}
