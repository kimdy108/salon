package com.project.salon.main.api.controller.setting;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.setting.system.HoursRegist;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.setting.SystemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/setting/system")
public class SystemController {
    private final AuditService auditService;
    private final SystemService systemService;

    private final String CONTROLLER_CATEGORY = "system";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> hoursRegist (@RequestBody HoursRegist hoursRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("hoursRegist")
                .auditDetail(hoursRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        systemService.hoursRegist(hoursRegist);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/info/list/{companyGuid}")
    public ResponseEntity<ResponseMsg> hoursInfoList(@PathVariable String companyGuid) {
        return ResponseMsg.successResponse(systemService.hoursInfoList(companyGuid));
    }
}
