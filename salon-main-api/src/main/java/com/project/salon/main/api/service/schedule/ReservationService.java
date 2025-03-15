package com.project.salon.main.api.service.schedule;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.schedule.SalonEmployment;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.domain.setting.SalonStyle;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.schedule.EmploymentCategory;
import com.project.salon.main.api.dto.schedule.reservation.ReservationRegist;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepository;
import com.project.salon.main.api.repository.schedule.SalonReservationRepository;
import com.project.salon.main.api.repository.setting.SalonStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final SalonCompanyRepository salonCompanyRepository;
    private final SalonAdminRepository salonAdminRepository;
    private final SalonStyleRepository salonStyleRepository;
    private final SalonEmploymentRepository salonEmploymentRepository;
    private final SalonReservationRepository salonReservationRepository;

    @Transactional
    public void reservationRegist(ReservationRegist reservationRegist) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(reservationRegist.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("고객사가 존재하지 않습니다.");

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(reservationRegist.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");
        if (!salonCompany.getSeq().equals(salonAdmin.getCompanySeq())) throw new RuntimeException("사용자가 존재하지 않습니다.");

        String[] dateArray = reservationRegist.getReservationDate().split("T")[0].split("-");
        if (dateArray.length != 3) throw new RuntimeException("날짜 데이터에 오류가 있습니다.");

        String[] timeArray = reservationRegist.getReservationDate().split("T")[1].replace("+09:00", "").split(":");
        if (timeArray.length != 3) throw new RuntimeException("시간 데이터에 오류가 있습니다.");

        SalonEmployment salonEmployment = salonEmploymentRepository.findSalonEmploymentByAdminSeqAndEmploymentYearAndEmploymentMonthAndEmploymentDay(salonAdmin.getSeq(), dateArray[0], dateArray[1], dateArray[2]);
        if (!EmploymentCategory.EMPOLYMENT.equals(salonEmployment.getEmploymentCategory())) throw new RuntimeException("근무일이 아닙니다.");

        SalonStyle salonStyle = salonStyleRepository.findSalonStyleByStyleGuid(reservationRegist.getStyleGuid());
        if (salonStyle == null) throw new RuntimeException("스타일이 존재하지 않습니다.");
        if (!salonAdmin.getSeq().equals(salonStyle.getSeq())) throw new RuntimeException("스타일이 존재하지 않습니다.");

        LocalDateTime nowDate = LocalDateTime.now();
        String reservationEndHour = "";
        String reservationEndMinute = "";

        if (IsYesNo.YES.equals(salonStyle.getIsMiddleTime())) {
            // todo... 중간 시간 사용시 중간 시간 제외 하고 계산
        } else {
            // todo... 시술 종료 시간 확인
        }

        salonReservationRepository.save(SalonReservation.builder()
                .reservationGuid(UUID.randomUUID())
                .adminSeq(salonAdmin.getSeq())
                .adminGuid(salonAdmin.getAdminGuid())
                .styleSeq(salonStyle.getSeq())
                .styleGuid(salonStyle.getStyleGuid())
                .reservationYear(dateArray[0])
                .reservationMonth(dateArray[1])
                .reservationDay(dateArray[2])
                .reservationHour(timeArray[0])
                .reservationMinute(timeArray[1])
                .reservationEndHour(reservationEndHour)
                .reservationEndMinute(reservationEndMinute)
                .clientName(reservationRegist.getClientName())
                .clientNumber(reservationRegist.getClientNumber())
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote(reservationRegist.getDescriptionNote())
                .build()
        );
    }
}
