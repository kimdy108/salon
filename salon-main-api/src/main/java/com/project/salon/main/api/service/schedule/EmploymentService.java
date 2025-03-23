package com.project.salon.main.api.service.schedule;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.schedule.SalonEmployment;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.domain.setting.SalonSystem;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.schedule.EmploymentCategory;
import com.project.salon.main.api.dto.constant.system.HoursCategory;
import com.project.salon.main.api.dto.schedule.employment.EmploymentInfo;
import com.project.salon.main.api.dto.schedule.employment.EmploymentList;
import com.project.salon.main.api.dto.schedule.employment.EmploymentRegist;
import com.project.salon.main.api.dto.schedule.employment.EmploymentUpdate;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepository;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepositoryImpl;
import com.project.salon.main.api.repository.schedule.SalonReservationRepository;
import com.project.salon.main.api.repository.setting.SalonSystemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmploymentService {
    private final SalonCompanyRepository salonCompanyRepository;
    private final SalonAdminRepository salonAdminRepository;
    private final SalonEmploymentRepository salonEmploymentRepository;
    private final SalonReservationRepository salonReservationRepository;
    private final SalonSystemRepository salonSystemRepository;

    private final SalonEmploymentRepositoryImpl salonEmploymentRepositoryImpl;

    @Transactional
    public void employmentRegist (EmploymentRegist employmentRegist) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(employmentRegist.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("고객사가 존재하지 않습니다.");

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(employmentRegist.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");
        if (!salonCompany.getSeq().equals(salonAdmin.getCompanySeq())) throw new RuntimeException("사용자가 존재하지 않습니다.");

        String[] dateArray = employmentRegist.getEmploymentDate().split("-");
        if (dateArray.length != 3) throw new RuntimeException("날짜 데이터에 오류가 있습니다.");

        LocalDate employmentDate = LocalDate.parse(employmentRegist.getEmploymentDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DayOfWeek dayOfWeek = employmentDate.getDayOfWeek();

        SalonSystem salonSystem = salonSystemRepository.findSalonSystemByCompanyGuidAndDayOfWeek(employmentRegist.getCompanyGuid(), dayOfWeek);
        if (salonSystem == null) throw new RuntimeException("영업시간 정보가 없습니다.");
        if (employmentRegist.getEmploymentCategory() == EmploymentCategory.EMPLOYMENT && salonSystem.getHoursCategory() != HoursCategory.EMPLOYMENT) throw new RuntimeException("휴무일 입니다.");

        SalonEmployment salonEmployment = salonEmploymentRepository.findSalonEmploymentByAdminSeqAndEmploymentYearAndEmploymentMonthAndEmploymentDay(salonAdmin.getSeq(), dateArray[0], dateArray[1], dateArray[2]);
        if (salonEmployment != null) throw new RuntimeException("등록된 근태 정보가 있습니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        salonEmploymentRepository.save(SalonEmployment.builder()
                .employmentGuid(UUID.randomUUID())
                .adminSeq(salonAdmin.getSeq())
                .adminGuid(salonAdmin.getAdminGuid())
                .employmentYear(dateArray[0])
                .employmentMonth(dateArray[1])
                .employmentDay(dateArray[2])
                .employmentCategory(employmentRegist.getEmploymentCategory())
                .isActive(IsYesNo.YES)
                .insertData(nowDate)
                .updateData(nowDate)
                .descriptionNote(employmentRegist.getDescriptionNote())
                .build()
        );
    }

    @Transactional
    public void employmentUpdate (EmploymentUpdate employmentUpdate) {
        SalonEmployment salonEmployment = salonEmploymentRepository.findSalonEmploymentByEmploymentGuid(employmentUpdate.getEmploymentGuid());
        if (salonEmployment == null) throw new RuntimeException("근태 정보가 존재하지 않습니다.");

        if (employmentUpdate.getEmploymentCategory() != EmploymentCategory.EMPLOYMENT) {
            SalonReservation salonReservation = salonReservationRepository.findSalonReservationByAdminGuidAndReservationYearAndReservationMonthAndReservationDay(salonEmployment.getAdminGuid(), salonEmployment.getEmploymentYear(), salonEmployment.getEmploymentMonth(), salonEmployment.getEmploymentDay());
            if (salonReservation != null) throw new RuntimeException("예약 정보가 있습니다.");
        } else {
            SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(salonEmployment.getAdminGuid());
            if (salonAdmin == null) throw new RuntimeException("사용자 정보가 존재하지 않습니다.");

            String employmentDateString = salonEmployment.getEmploymentYear() + "-" + salonEmployment.getEmploymentMonth() + "-" + salonEmployment.getEmploymentDay();
            LocalDate employmentDate = LocalDate.parse(employmentDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DayOfWeek dayOfWeek = employmentDate.getDayOfWeek();

            SalonSystem salonSystem = salonSystemRepository.findSalonSystemByCompanyGuidAndDayOfWeek(salonAdmin.getCompanyGuid(), dayOfWeek);
            if (salonSystem == null) throw new RuntimeException("영업시간 정보가 없습니다.");
            if (salonSystem.getHoursCategory() != HoursCategory.EMPLOYMENT) throw new RuntimeException("영업시간 정보가 없습니다.");
        }

        salonEmployment.update(
                employmentUpdate.getEmploymentCategory(),
                LocalDateTime.now(),
                employmentUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void employmentDelete (String employmentGuid) {
        SalonEmployment salonEmployment = salonEmploymentRepository.findSalonEmploymentByEmploymentGuid(UUID.fromString(employmentGuid));
        if (salonEmployment == null) throw new RuntimeException("근태 정보가 존재하지 않습니다.");

        SalonReservation salonReservation = salonReservationRepository.findSalonReservationByAdminGuidAndReservationYearAndReservationMonthAndReservationDay(salonEmployment.getAdminGuid(), salonEmployment.getEmploymentYear(), salonEmployment.getEmploymentMonth(), salonEmployment.getEmploymentDay());
        if (salonReservation != null) throw new RuntimeException("예약 정보가 있습니다.");

        salonEmploymentRepository.delete(salonEmployment);
    }

    public List<EmploymentList> employmentList (String employmentDate, String companyGuid) {
        String[] dateArray = employmentDate.split("-");
        if (dateArray.length != 2) throw new RuntimeException("날짜 데이터에 오류가 있습니다.");

        return salonEmploymentRepositoryImpl.findEmploymentListByYearAndMonth(dateArray[0], dateArray[1], UUID.fromString(companyGuid));
    }

    public EmploymentInfo employmentInfo (String employmentGuid) {
        return salonEmploymentRepositoryImpl.findEmploymentInfo(UUID.fromString(employmentGuid));
    }
}
