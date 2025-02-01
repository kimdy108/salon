package com.project.salon.main.api.auth;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.company.SalonCompany;
import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.company.SalonCompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
public class Master {
    @Autowired
    private SalonAdminRepository salonAdminRepository;

    @Autowired
    private SalonCompanyRepository salonCompanyRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void makeCompany() {
        UUID companyGuid = UUID.randomUUID();
        String companyNumber = "001010001";
        String companyName = "Salon";
        String companyAddress = "Seoul";
        String companyPhone = "01012341234";
        IsYesNo isActive = IsYesNo.YES;
        LocalDateTime nowDate = LocalDateTime.now();
        String descriptionNote = "";

        SalonCompany salonCompany = SalonCompany.builder()
                .companyGuid(companyGuid)
                .companyNumber(companyNumber)
                .companyName(companyName)
                .companyAddress(companyAddress)
                .companyPhone(companyPhone)
                .isActive(isActive)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote(descriptionNote)
                .build();

        salonCompanyRepository.save(salonCompany);
    }

    @Test
    void makeMaster() {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyNumber("001010001");

        LocalDateTime nowDate = LocalDateTime.now();
        UUID adminGuid = UUID.randomUUID();
        String adminID = "master";
        String adminPassword = passwordEncoder.encode("1234");
        UUID companyGuid = salonCompany.getCompanyGuid();
        Long companySeq = salonCompany.getSeq();
        String adminName = "Master";
        String adminPhone = "01012341234";
        String adminEmail = "master@salon.co.kr";
        AdminRole adminRole = AdminRole.MASTER;
        String adminType = "SYSTEM";
        LocalDateTime lastDate = LocalDateTime.parse("1970-01-01T00:00:00");
        IsYesNo isActive = IsYesNo.YES;
        LocalDateTime insertDate = nowDate;
        LocalDateTime updateDate = nowDate;
        String descriptionNote = "";

        SalonAdmin salonAdmin = SalonAdmin.builder()
                .adminGuid(adminGuid)
                .adminID(adminID)
                .adminPassword(adminPassword)
                .companyGuid(companyGuid)
                .companySeq(companySeq)
                .adminName(adminName)
                .adminPhone(adminPhone)
                .adminEmail(adminEmail)
                .adminRole(adminRole)
                .adminType(adminType)
                .lastDate(lastDate)
                .isActive(isActive)
                .insertDate(insertDate)
                .updateDate(updateDate)
                .descriptionNote(descriptionNote)
                .build();
        salonAdminRepository.save(salonAdmin);
    }
}
