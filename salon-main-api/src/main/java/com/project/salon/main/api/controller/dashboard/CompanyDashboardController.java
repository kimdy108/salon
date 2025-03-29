package com.project.salon.main.api.controller.dashboard;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.dashboard.CompanyDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/dashboard/company")
public class CompanyDashboardController {
    private final CompanyDashboardService companyDashboardService;

    @GetMapping("/current")
    public ResponseEntity<ResponseMsg> getCurrentCompany() {
        return ResponseMsg.successResponse(companyDashboardService.getCompanyCurrent());
    }

    @GetMapping("/current/month")
    public ResponseEntity<ResponseMsg> getCurrentMonth() {
        return ResponseMsg.successResponse(companyDashboardService.getCompanyByMonth());
    }
}
