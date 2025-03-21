package com.project.salon.main.api.controller.schedule;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.schedule.reservation.ReservationRegist;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.schedule.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{reservationGuid}")
    public ResponseEntity<ResponseMsg> reservationDelete(@PathVariable String reservationGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("reservationDelete")
                .auditDetail(reservationGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        reservationService.reservationDelete(reservationGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/info/{reservationGuid}")
    public ResponseEntity<ResponseMsg> reservationInfo(@PathVariable String reservationGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("reservationInfo")
                .auditDetail(reservationGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        return ResponseMsg.successResponse(reservationService.reservationInfo(reservationGuid));
    }

    @GetMapping("/month/list")
    public ResponseEntity<ResponseMsg> reservationMonthList(
            @RequestParam String searchDate,
            @RequestParam String companyGuid,
            @RequestParam String userGuid
    ) {
        return ResponseMsg.successResponse(reservationService.reservationMonthList(searchDate, companyGuid, userGuid));
    }

    @GetMapping("/day/list")
    public ResponseEntity<ResponseMsg> reservationDayList(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String companyGuid,
            @RequestParam String userGuid
    ) {
        return ResponseMsg.successResponse(reservationService.reservationDayList(startDate, endDate, companyGuid, userGuid));
    }
}
