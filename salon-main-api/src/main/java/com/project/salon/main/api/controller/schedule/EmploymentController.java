package com.project.salon.main.api.controller.schedule;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.schedule.employment.EmploymentRegist;
import com.project.salon.main.api.dto.schedule.employment.EmploymentUpdate;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.schedule.EmploymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/schedule/employment")
public class EmploymentController {
    private final AuditService auditService;
    private final EmploymentService employmentService;

    private final String CONTROLLER_CATEGORY = "employment";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> employmentRegist(@RequestBody EmploymentRegist employmentRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("employmentRegist")
                .auditDetail(employmentRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        employmentService.employmentRegist(employmentRegist);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> employmentUpdate(@RequestBody EmploymentUpdate employmentUpdate, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("employmentUpdate")
                .auditDetail(employmentUpdate)
                .build();
        auditService.auditRegist(auditLogRegist);

        employmentService.employmentUpdate(employmentUpdate);
        return ResponseMsg.successResponse("success");
    }

    @DeleteMapping("/delete/{employmentGuid}")
    public ResponseEntity<ResponseMsg> employmentDelete(@PathVariable String employmentGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("employmentDelete")
                .auditDetail(employmentGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        employmentService.employmentDelete(employmentGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseMsg> employmentList(
            @RequestParam String employmentDate,
            @RequestParam String companyGuid
            ) {
        return ResponseMsg.successResponse(employmentService.employmentList(employmentDate, companyGuid));
    }

    @GetMapping("/info/{employmentGuid}")
    public ResponseEntity<ResponseMsg> employmentInfo(@PathVariable String employmentGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("employmentInfo")
                .auditDetail(employmentGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        return ResponseMsg.successResponse(employmentService.employmentInfo(employmentGuid));
    }
}
