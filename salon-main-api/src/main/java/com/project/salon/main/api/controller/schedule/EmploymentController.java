package com.project.salon.main.api.controller.schedule;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.schedule.employment.EmploymentRegist;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.schedule.EmploymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/schedule/employment")
public class EmploymentController {
    private final AuditService auditService;
    private final EmploymentService employmentService;

    private final String CONTROLLER_CATEGORY = "employment";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> regist(@RequestBody EmploymentRegist employmentRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("regist")
                .auditDetail(employmentRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        employmentService.regist(employmentRegist);
        return ResponseMsg.successResponse("success");
    }
}
