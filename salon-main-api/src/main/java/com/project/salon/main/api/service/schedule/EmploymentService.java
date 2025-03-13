package com.project.salon.main.api.service.schedule;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.schedule.SalonEmployment;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.schedule.employment.EmploymentRegist;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmploymentService {
    private final SalonCompanyRepository salonCompanyRepository;
    private final SalonAdminRepository salonAdminRepository;
    private final SalonEmploymentRepository salonEmploymentRepository;

    @Transactional
    public void regist (EmploymentRegist employmentRegist) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(employmentRegist.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("고객사가 존재하지 않습니다.");

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(employmentRegist.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        salonEmploymentRepository.save(SalonEmployment.builder()
                .employmentGuid(UUID.randomUUID())
                .adminSeq(salonAdmin.getSeq())
                .adminGuid(salonAdmin.getAdminGuid())
                .employmentDate(employmentRegist.getEmploymentDate())
                .employmentCategory(employmentRegist.getEmploymentCategory())
                .isActive(IsYesNo.YES)
                .insertData(nowDate)
                .updateData(nowDate)
                .descriptionNote(employmentRegist.getDescriptionNote())
                .build()
        );
    }
}
