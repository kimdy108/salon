package com.project.salon.main.api.service.report;

import com.project.salon.main.api.dto.report.audit.AuditLogList;
import com.project.salon.main.api.repository.report.SalonAuditLogRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final SalonAuditLogRepositoryImpl auditLogRepositoryImpl;

    public Page<AuditLogList> auditLogListPage(Long startTimestamp, Long endTimestamp, String searchType, String searchValue, Long offset, int limit) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return auditLogRepositoryImpl.findAuditLogListPage(startTimestamp, endTimestamp, searchType, searchValue, offset, limit, pageable);
    }
}
