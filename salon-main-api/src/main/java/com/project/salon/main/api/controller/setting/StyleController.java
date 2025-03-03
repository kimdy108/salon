package com.project.salon.main.api.controller.setting;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.setting.style.StyleActive;
import com.project.salon.main.api.dto.setting.style.StyleRegist;
import com.project.salon.main.api.dto.setting.style.StyleUpdate;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.setting.StyleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/style")
public class StyleController {
    private final AuditService auditService;
    private final StyleService styleService;

    private final String CONTROLLER_CATEGORY = "style";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> styleRegist(@RequestBody StyleRegist styleRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("styleRegist")
                .auditDetail(styleRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        styleService.styleRegist(styleRegist);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> styleUpdate(@RequestBody StyleUpdate styleUpdate, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("styleUpdate")
                .auditDetail(styleUpdate)
                .build();
        auditService.auditRegist(auditLogRegist);

        styleService.styleUpdate(styleUpdate);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/active")
    public ResponseEntity<ResponseMsg> styleActive(@RequestBody StyleActive styleActive, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("styleActive")
                .auditDetail(styleActive)
                .build();
        auditService.auditRegist(auditLogRegist);

        styleService.styleActive(styleActive);
        return ResponseMsg.successResponse("success");
    }

    @DeleteMapping("/delete/{styleGuid}")
    public ResponseEntity<ResponseMsg> styleDelete(@PathVariable String styleGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("styleDelete")
                .auditDetail(styleGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        styleService.styleDelete(styleGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> styleListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit,
            @RequestParam String companyGuid
    ) {
        return ResponseMsg.successResponse(styleService.styleListPage(searchType, searchValue, offset, limit, companyGuid));
    }
}
