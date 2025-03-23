package com.project.salon.main.api.service.setting;

import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.setting.SalonSystem;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.system.HoursCategory;
import com.project.salon.main.api.dto.setting.system.HoursInfo;
import com.project.salon.main.api.dto.setting.system.HoursRegist;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.setting.SalonSystemRepository;
import com.project.salon.main.api.repository.setting.SalonSystemRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final SalonCompanyRepository salonCompanyRepository;
    private final SalonSystemRepository salonSystemRepository;

    private final SalonSystemRepositoryImpl salonSystemRepositoryImpl;

    @Transactional
    public void hoursRegist(HoursRegist hoursRegist) {
        SalonSystem salonSystem = salonSystemRepository.findSalonSystemByCompanyGuidAndDayOfWeek(hoursRegist.getCompanyGuid(), hoursRegist.getDayOfWeek());
        if (salonSystem != null) salonSystemRepository.delete(salonSystem);

        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(hoursRegist.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("고객사가 존재하지 않습니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        salonSystemRepository.save(SalonSystem.builder()
                .companySeq(salonCompany.getSeq())
                .companyGuid(salonCompany.getCompanyGuid())
                .dayOfWeek(hoursRegist.getDayOfWeek())
                .hoursCategory(hoursRegist.getHoursCategory())
                .startTimeHour(hoursRegist.getHoursCategory() == HoursCategory.EMPLOYMENT ? hoursRegist.getStartTime().split(":")[0] : "00")
                .startTimeMinute(hoursRegist.getHoursCategory() == HoursCategory.EMPLOYMENT ? hoursRegist.getStartTime().split(":")[1] : "00")
                .endTimeHour(hoursRegist.getHoursCategory() == HoursCategory.EMPLOYMENT ? hoursRegist.getEndTime().split(":")[0] : "00")
                .endTimeMinute(hoursRegist.getHoursCategory() == HoursCategory.EMPLOYMENT ? hoursRegist.getEndTime().split(":")[1] : "00")
                .insertDate(nowDate)
                .updateDate(nowDate)
                .isActive(IsYesNo.YES)
                .descriptionNote("")
                .build()
        );
    }

    public List<HoursInfo> hoursInfoList(String companyGuid) {
        return salonSystemRepositoryImpl.findHoursInfoList(UUID.fromString(companyGuid));
    }
}
