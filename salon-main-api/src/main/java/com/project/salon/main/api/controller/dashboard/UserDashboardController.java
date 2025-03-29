package com.project.salon.main.api.controller.dashboard;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.dashboard.UserDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/dashboard/user")
public class UserDashboardController {
    private final UserDashboardService userDashboardService;

    @GetMapping("/current")
    public ResponseEntity<ResponseMsg> getCurrentUser() {
        return ResponseMsg.successResponse(userDashboardService.getUserCurrent());
    }

    @GetMapping("/current/month")
    public ResponseEntity<ResponseMsg> getCurrentUserMonth() {
        return ResponseMsg.successResponse(userDashboardService.getUserByMonth());
    }
}
