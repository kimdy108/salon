package com.project.salon.main.api.service.dashboard;

import com.project.salon.main.api.dto.dashboard.user.UserByMonth;
import com.project.salon.main.api.dto.dashboard.user.UserCurrent;
import com.project.salon.main.api.repository.admin.SalonAdminRepositoyImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDashboardService {
    private final SalonAdminRepositoyImpl salonAdminRepositoyImpl;

    public UserCurrent getUserCurrent() {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();

        return new UserCurrent(
                salonAdminRepositoyImpl.findUserCurrent(0, 0, true).intValue(),
                salonAdminRepositoyImpl.findUserCurrent(year, month, true).intValue(),
                salonAdminRepositoyImpl.findUserCurrent(0, 0, false).intValue()
        );
    }

    public List<UserByMonth> getUserByMonth() {
        return salonAdminRepositoyImpl.findUserByMonth(LocalDateTime.now().getYear());
    }
}
