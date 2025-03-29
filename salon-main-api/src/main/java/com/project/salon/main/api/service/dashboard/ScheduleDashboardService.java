package com.project.salon.main.api.service.dashboard;

import com.project.salon.main.api.dto.dashboard.schedule.ScheduleByEmployment;
import com.project.salon.main.api.dto.dashboard.schedule.ScheduleByMonth;
import com.project.salon.main.api.dto.dashboard.schedule.ScheduleCurrent;
import com.project.salon.main.api.repository.schedule.SalonEmploymentRepositoryImpl;
import com.project.salon.main.api.repository.schedule.SalonReservationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleDashboardService {
    private final SalonReservationRepositoryImpl salonReservationRepositoryImpl;
    private final SalonEmploymentRepositoryImpl salonEmploymentRepositoryImpl;

    public ScheduleCurrent getScheduleCurrent(String companyGuid) {
        String year = String.valueOf(LocalDateTime.now().getYear());
        String month = String.format("%02d", LocalDateTime.now().getMonthValue());

        return new ScheduleCurrent(
                salonReservationRepositoryImpl.findScheduleCurrent(year, "0", "0", UUID.fromString(companyGuid)).intValue(),
                salonReservationRepositoryImpl.findScheduleCurrent(year, month, "0", UUID.fromString(companyGuid)).intValue()
        );
    }

    public List<ScheduleByMonth> getScheduleByMonth(String companyGuid) {
        String year = String.valueOf(LocalDateTime.now().getYear());
        return salonReservationRepositoryImpl.findScheduleByMonth(year, UUID.fromString(companyGuid));
    }

    public ScheduleByEmployment getScheduleByEmployment(String companyGuid) {
        String year = String.valueOf(LocalDateTime.now().getYear());
        String month = String.format("%02d", LocalDateTime.now().getMonthValue());
        String day = String.format("%02d", LocalDateTime.now().getDayOfMonth());

        return new ScheduleByEmployment(
                salonReservationRepositoryImpl.findScheduleCurrent(year, month, day, UUID.fromString(companyGuid)).intValue(),
                salonEmploymentRepositoryImpl.findEmploymentCurrent(year, month, day, UUID.fromString(companyGuid)).intValue()
        );
    }
}
