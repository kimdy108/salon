package com.project.salon.main.api.controller.manage;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.manage.master.MasterActive;
import com.project.salon.main.api.dto.manage.master.MasterRegist;
import com.project.salon.main.api.dto.manage.master.MasterUpdate;
import com.project.salon.main.api.dto.manage.master.MasterUpdatePassword;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.manage.MasterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/manage/master")
public class MasterController {
    private final AuditService auditService;
    private final MasterService masterService;

    private final String CONTROLLER_CATEGORY = "master";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> masterRegist(@RequestBody MasterRegist masterRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("masterRegist")
                .auditDetail(masterRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        masterService.masterRegist(masterRegist);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> masterUpdate(@RequestBody MasterUpdate masterUpdate, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("masterUpdate")
                .auditDetail(masterUpdate)
                .build();
        auditService.auditRegist(auditLogRegist);

        masterService.masterUpdate(masterUpdate);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update/password")
    public ResponseEntity<ResponseMsg> masterUpdatePassword(@RequestBody MasterUpdatePassword masterUpdatePassword, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("masterUpdatePassword")
                .auditDetail(masterUpdatePassword)
                .build();
        auditService.auditRegist(auditLogRegist);

        masterService.masterUpdatePassword(masterUpdatePassword);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/active")
    public ResponseEntity<ResponseMsg> masterActive(@RequestBody MasterActive masterActive, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("masterActive")
                .auditDetail(masterActive)
                .build();
        auditService.auditRegist(auditLogRegist);

        masterService.masterActive(masterActive);
        return ResponseMsg.successResponse("success");
    }

    @DeleteMapping("/delete/{masterGuid}")
    public ResponseEntity<ResponseMsg> masterDelete(@PathVariable String masterGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("masterDelete")
                .auditDetail(masterGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        masterService.masterDelete(masterGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> masterList(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        return ResponseMsg.successResponse(masterService.masterListPage(searchType, searchValue, offset, limit));
    }

    @GetMapping("/info/{masterGuid}")
    public ResponseEntity<ResponseMsg> masterInfo(@PathVariable String masterGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("masterInfo")
                .auditDetail(masterGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        return ResponseMsg.successResponse(masterService.masterInfo(masterGuid));
    }
}
