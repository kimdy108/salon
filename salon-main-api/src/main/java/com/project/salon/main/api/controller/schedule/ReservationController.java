package com.project.salon.main.api.controller.schedule;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.schedule.reservation.ReservationRegist;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.schedule.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/schedule/reservation")
public class ReservationController {
    private final AuditService auditService;
    private final ReservationService reservationService;

    private final String CONTROLLER_CATEGORY = "reservation";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> reservationRegist(@RequestBody ReservationRegist reservationRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("reservationRegist")
                .auditDetail(reservationRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        reservationService.reservationRegist(reservationRegist);
        return ResponseMsg.successResponse("success");
    }
}
