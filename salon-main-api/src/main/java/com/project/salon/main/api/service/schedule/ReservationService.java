package com.project.salon.main.api.service.schedule;

import com.google.gson.Gson;
import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.schedule.SalonEmployment;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.domain.setting.SalonStyle;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.schedule.EmploymentCategory;
import com.project.salon.main.api.dto.schedule.reservation.ReservationRegist;
import com.project.salon.main.api.dto.schedule.reservation.ReservationUpdate;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepository;
import com.project.salon.main.api.repository.schedule.SalonReservationRepository;
import com.project.salon.main.api.repository.setting.SalonStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final Gson gson;

    private final SalonAdminRepository salonAdminRepository;
    private final SalonStyleRepository salonStyleRepository;
    private final SalonEmploymentRepository salonEmploymentRepository;
    private final SalonReservationRepository salonReservationRepository;

    @Transactional
    public void reservationRegist(ReservationRegist reservationRegist) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(reservationRegist.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");
        if (!IsYesNo.YES.equals(salonAdmin.getIsActive())) throw new RuntimeException("사용자가 존재하지 않습니다.");

        String[] dateArray = reservationRegist.getReservationDate().split("T")[0].split("-");
        if (dateArray.length != 3) throw new RuntimeException("날짜 데이터에 오류가 있습니다.");

        String[] timeArray = reservationRegist.getReservationDate().split("T")[1].replace("+09:00", "").split(":");
        if (timeArray.length != 3) throw new RuntimeException("시간 데이터에 오류가 있습니다.");

        SalonEmployment salonEmployment = salonEmploymentRepository.findSalonEmploymentByAdminSeqAndEmploymentYearAndEmploymentMonthAndEmploymentDay(salonAdmin.getSeq(), dateArray[0], dateArray[1], dateArray[2]);
        if (salonEmployment == null) throw new RuntimeException("근무일이 아닙니다.");
        if (!EmploymentCategory.EMPOLYMENT.equals(salonEmployment.getEmploymentCategory())) throw new RuntimeException("근무일이 아닙니다.");

        SalonStyle salonStyle = salonStyleRepository.findSalonStyleByStyleGuid(reservationRegist.getStyleGuid());
        if (salonStyle == null) throw new RuntimeException("스타일이 존재하지 않습니다.");
        if (!salonAdmin.getSeq().equals(salonStyle.getAdminSeq())) throw new RuntimeException("스타일이 존재하지 않습니다.");

        UUID reservationPartnerGuid = UUID.randomUUID();
        LocalDateTime nowDate = LocalDateTime.now();

        if (IsYesNo.YES.equals(salonStyle.getIsMiddleTime())) {
            List<Double> middleTimeList = gson.fromJson(salonStyle.getMiddleTime(), List.class);

            for (int i = 0; i < salonStyle.getStyleDuration() / 30; i++) {
                if (!middleTimeList.contains((double) (30 * (i+1)))) {
                    LocalTime reservationTime = LocalTime.parse(reservationRegist.getReservationDate().split("T")[1].replace("+09:00", ""));
                    LocalTime startTime = reservationTime.plusMinutes(30 * i);
                    LocalTime endTime = startTime.plusMinutes(30);

                    SalonReservation salonReservation = salonReservationRepository.findSalonReservationByReservationYearAndReservationMonthAndReservationDayAndReservationHourAndReservationMinute(
                            dateArray[0],
                            dateArray[1],
                            dateArray[2],
                            String.valueOf(startTime.getHour()),
                            String.valueOf(startTime.getMinute())
                    );
                    if (salonReservation != null) throw new RuntimeException("예약 시간 중복입니다.");

                    salonReservationRepository.save(SalonReservation.builder()
                            .reservationGuid(UUID.randomUUID())
                            .reservationPartnerGuid(reservationPartnerGuid)
                            .adminSeq(salonAdmin.getSeq())
                            .adminGuid(salonAdmin.getAdminGuid())
                            .styleSeq(salonStyle.getSeq())
                            .styleGuid(salonStyle.getStyleGuid())
                            .reservationYear(dateArray[0])
                            .reservationMonth(dateArray[1])
                            .reservationDay(dateArray[2])
                            .reservationHour(String.valueOf(startTime.getHour()))
                            .reservationMinute(String.valueOf(startTime.getMinute()))
                            .reservationEndHour(String.valueOf(endTime.getHour()))
                            .reservationEndMinute(String.valueOf(endTime.getMinute()))
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
        } else {
            LocalTime startTime = LocalTime.parse(reservationRegist.getReservationDate().split("T")[1].replace("+09:00", ""));
            LocalTime endTime = startTime.plusMinutes(salonStyle.getStyleDuration());

            for (int i = 0; i < salonStyle.getStyleDuration() / 30; i++) {
                LocalTime validationTime = startTime.plusMinutes(30 * i);

                SalonReservation salonReservation = salonReservationRepository.findSalonReservationByReservationYearAndReservationMonthAndReservationDayAndReservationHourAndReservationMinute(
                        dateArray[0],
                        dateArray[1],
                        dateArray[2],
                        String.valueOf(validationTime.getHour()),
                        String.valueOf(validationTime.getMinute())
                );
                if (salonReservation != null) throw new RuntimeException("예약 시간 중복입니다.");
            }

            salonReservationRepository.save(SalonReservation.builder()
                    .reservationGuid(UUID.randomUUID())
                    .reservationPartnerGuid(reservationPartnerGuid)
                    .adminSeq(salonAdmin.getSeq())
                    .adminGuid(salonAdmin.getAdminGuid())
                    .styleSeq(salonStyle.getSeq())
                    .styleGuid(salonStyle.getStyleGuid())
                    .reservationYear(dateArray[0])
                    .reservationMonth(dateArray[1])
                    .reservationDay(dateArray[2])
                    .reservationHour(timeArray[0])
                    .reservationMinute(timeArray[1])
                    .reservationEndHour(String.valueOf(endTime.getHour()))
                    .reservationEndMinute(String.valueOf(endTime.getMinute()))
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

    @Transactional
    public void reservationUpdate(ReservationUpdate reservationUpdate) {
        reservationDelete(reservationUpdate.getReservationGuid().toString());

        reservationRegist(new ReservationRegist(
                reservationUpdate.getUserGuid(),
                reservationUpdate.getStyleGuid(),
                reservationUpdate.getReservationDate(),
                reservationUpdate.getClientName(),
                reservationUpdate.getClientNumber(),
                reservationUpdate.getDescriptionNote()
        ));
    }

    @Transactional
    public void reservationDelete(String reservationGuid) {
        SalonReservation salonReservation = salonReservationRepository.findSalonReservationByReservationGuid(UUID.fromString(reservationGuid));
        if (salonReservation == null) throw new RuntimeException("존재하지 않은 예약입니다.");

        salonReservationRepository.deleteByReservationPartnerGuid(salonReservation.getReservationPartnerGuid());
    }
}
