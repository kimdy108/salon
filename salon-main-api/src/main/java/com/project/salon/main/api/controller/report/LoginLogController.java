package com.project.salon.main.api.controller.report;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.report.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/report/login")
public class LoginLogController {
    private final LoginLogService loginLogService;

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> loginLogListPage(
            @RequestParam Long startTimestamp,
            @RequestParam Long endTimestamp,
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit,
            @RequestParam String companyGuid
    ) {
        return ResponseMsg.successResponse(loginLogService.loginLogListPage(startTimestamp, endTimestamp, searchType, searchValue, offset, limit, companyGuid));
    }
}
