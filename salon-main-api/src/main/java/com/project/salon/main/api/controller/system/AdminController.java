package com.project.salon.main.api.controller.system;

import com.project.salon.main.api.dto.admin.AdminUpdate;
import com.project.salon.main.api.dto.admin.AdminUpdatePassword;
import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.system.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/setting/admin")
public class AdminController {
    private final AuditService auditService;
    private final AdminService adminService;

    private final String CONTROLLER_CATEGORY = "admin";

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> adminUpdate(@RequestBody AdminUpdate adminUpdate, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("adminUpdate")
                .auditDetail(adminUpdate)
                .build();
        auditService.auditRegist(auditLogRegist);

        adminService.adminUpdate(adminUpdate);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update/password")
    public ResponseEntity<ResponseMsg> adminUpdatePassword(@RequestBody AdminUpdatePassword adminUpdatePassword, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("adminUpdatePassword")
                .auditDetail(adminUpdatePassword)
                .build();
        auditService.auditRegist(auditLogRegist);

        adminService.adminUpdatePassword(adminUpdatePassword);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/info/{adminGuid}")
    public ResponseEntity<ResponseMsg> adminInfo(@PathVariable String adminGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("adminInfo")
                .auditDetail(adminGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        return ResponseMsg.successResponse(adminService.adminInfo(adminGuid));
    }
}
