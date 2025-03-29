package com.project.salon.main.api.service.dashboard;

import com.project.salon.main.api.dto.dashboard.company.CompanyByMonth;
import com.project.salon.main.api.dto.dashboard.company.CompanyCurrent;
import com.project.salon.main.api.repository.manage.SalonCompanyRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyDashboardService {
    private final SalonCompanyRepositoryImpl salonCompanyRepositoryImpl;

    public CompanyCurrent getCompanyCurrent() {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();

        return new CompanyCurrent(
                salonCompanyRepositoryImpl.findCompanyCurrent(0, 0, true).intValue(),
                salonCompanyRepositoryImpl.findCompanyCurrent(year, month, true).intValue(),
                salonCompanyRepositoryImpl.findCompanyCurrent(0, 0, false).intValue()
        );
    }

    public List<CompanyByMonth> getCompanyByMonth() {
        return salonCompanyRepositoryImpl.findCompanyByMonth(LocalDateTime.now().getYear());
    }
}
