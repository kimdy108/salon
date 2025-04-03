package com.project.salon.main.api.controller.dashboard;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.dashboard.ScheduleDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/dashboard/schedule")
public class ScheduleDashboardController {
    private final ScheduleDashboardService scheduleDashboardService;

    @GetMapping("/current")
    public ResponseEntity<ResponseMsg> getCurrentDashboard(@RequestParam String companyGuid) {
        return ResponseMsg.successResponse(scheduleDashboardService.getScheduleCurrent(companyGuid));
    }

    @GetMapping("/current/month")
    public ResponseEntity<ResponseMsg> getCurrentDashboardMonth(@RequestParam String companyGuid) {
        return ResponseMsg.successResponse(scheduleDashboardService.getScheduleByMonth(companyGuid));
    }

    @GetMapping("/current/day")
    public ResponseEntity<ResponseMsg> getCurrentDashboardDay(@RequestParam String companyGuid) {
        return ResponseMsg.successResponse(scheduleDashboardService.getScheduleByEmployment(companyGuid));
    }

    @GetMapping("/current/user")
    public ResponseEntity<ResponseMsg> getCurrentDashboardUser(@RequestParam String companyGuid) {
        return ResponseMsg.successResponse(scheduleDashboardService.getScheduleByUser(companyGuid));
    }
}
