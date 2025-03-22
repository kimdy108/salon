package com.project.salon.main.api.service.schedule;

import com.google.gson.Gson;
import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.schedule.SalonEmployment;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.domain.setting.SalonStyle;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.constant.schedule.EmploymentCategory;
import com.project.salon.main.api.dto.schedule.reservation.*;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepository;
import com.project.salon.main.api.repository.schedule.SalonReservationRepository;
import com.project.salon.main.api.repository.schedule.SalonReservationRepositoryImpl;
import com.project.salon.main.api.repository.setting.SalonStyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.EMPTY_UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final Gson gson;

    private final SalonAdminRepository salonAdminRepository;
    private final SalonStyleRepository salonStyleRepository;
    private final SalonEmploymentRepository salonEmploymentRepository;
    private final SalonReservationRepository salonReservationRepository;

    private final SalonReservationRepositoryImpl salonReservationRepositoryImpl;

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

                    SalonReservation salonReservation = salonReservationRepository.findSalonReservationByAdminGuidAndReservationYearAndReservationMonthAndReservationDayAndReservationHourAndReservationMinute(
                            reservationRegist.getUserGuid(),
                            dateArray[0],
                            dateArray[1],
                            dateArray[2],
                            String.format("%02d", startTime.getHour()),
                            String.format("%02d", startTime.getMinute())
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
                            .reservationHour(String.format("%02d", startTime.getHour()))
                            .reservationMinute(String.format("%02d", startTime.getMinute()))
                            .reservationEndHour(String.format("%02d", endTime.getHour()))
                            .reservationEndMinute(String.format("%02d", endTime.getMinute()))
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

                SalonReservation salonReservation = salonReservationRepository.findSalonReservationByAdminGuidAndReservationYearAndReservationMonthAndReservationDayAndReservationHourAndReservationMinute(
                        reservationRegist.getUserGuid(),
                        dateArray[0],
                        dateArray[1],
                        dateArray[2],
                        String.format("%02d", validationTime.getHour()),
                        String.format("%02d", validationTime.getMinute())
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
                    .reservationEndHour(String.format("%02d", endTime.getHour()))
                    .reservationEndMinute(String.format("%02d", endTime.getMinute()))
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
    public void reservationDelete(String reservationGuid) {
        SalonReservation salonReservation = salonReservationRepository.findSalonReservationByReservationGuid(UUID.fromString(reservationGuid));
        if (salonReservation == null) throw new RuntimeException("존재하지 않은 예약입니다.");

        salonReservationRepository.deleteByReservationPartnerGuid(salonReservation.getReservationPartnerGuid());
    }

    public ReservationInfo reservationInfo(String reservationGuid) {
        SalonReservation salonReservation = salonReservationRepository.findSalonReservationByReservationGuid(UUID.fromString(reservationGuid));
        if (salonReservation == null) throw new RuntimeException("등록된 예약이 없습니다.");

        return salonReservationRepositoryImpl.findReservationInfo(UUID.fromString(reservationGuid));
    }

    public List<ReservationMonthList> reservationMonthList (String searchDate, String companyGuid, String userGuid) {
        String[] dateArray = searchDate.split("-");
        if (dateArray.length != 2) throw new RuntimeException("날짜 데이터가 정확하지 않습니다.");

        if (userGuid == null || userGuid.isEmpty()) userGuid = EMPTY_UUID.toString();

        return salonReservationRepositoryImpl.findReservationByMonth(dateArray[0], dateArray[1], UUID.fromString(companyGuid), UUID.fromString(userGuid));
    }

    public List<ReservationDayList> reservationDayList (String startDate, String endDate, String companyGuid, String userGuid) {
        String[] startDateArray = startDate.split("-");
        if (startDateArray.length != 3) throw new RuntimeException("날짜 데이터가 정확하지 않습니다.");

        String[] endDateArray = endDate.split("-");
        if (endDateArray.length != 3) throw new RuntimeException("날짜 데이터가 정확하지 않습니다.");

        if (userGuid == null || userGuid.isEmpty()) userGuid = EMPTY_UUID.toString();

        return salonReservationRepositoryImpl.findReservationByDay(startDateArray[0], startDateArray[1], startDateArray[2], endDateArray[0], endDateArray[1], endDateArray[2], UUID.fromString(companyGuid), UUID.fromString(userGuid));
    }
}
