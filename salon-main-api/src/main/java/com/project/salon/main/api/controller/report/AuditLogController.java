package com.project.salon.main.api.controller.report;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.report.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/report/audit")
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> auditLogListPage(
            @RequestParam Long startTimestamp,
            @RequestParam Long endTimestamp,
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        return ResponseMsg.successResponse(auditLogService.auditLogListPage(startTimestamp, endTimestamp, searchType, searchValue, offset, limit));
    }
}
