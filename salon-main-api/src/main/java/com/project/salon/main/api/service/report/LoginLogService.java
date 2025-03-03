package com.project.salon.main.api.service.report;

import com.project.salon.main.api.dto.report.login.LoginLogList;
import com.project.salon.main.api.repository.report.SalonLoginLogRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginLogService {
    private final SalonLoginLogRepositoryImpl salonLoginLogRepositoryImpl;

    public Page<LoginLogList> loginLogListPage(Long startTimeStamp, Long endTimeStamp, String searchType, String searchValue, Long offset, int limit, String companyGuid) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return salonLoginLogRepositoryImpl.findLoginListPage(startTimeStamp, endTimeStamp, searchType, searchValue, offset, limit, pageable, UUID.fromString(companyGuid));
    }
}
