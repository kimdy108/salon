package com.project.salon.main.api.controller.manage;

import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.manage.user.UserActive;
import com.project.salon.main.api.dto.manage.user.UserRegist;
import com.project.salon.main.api.dto.manage.user.UserUpdate;
import com.project.salon.main.api.dto.manage.user.UserUpdatePassword;
import com.project.salon.main.api.service.component.AuditService;
import com.project.salon.main.api.service.manage.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/user")
public class UserController {
    private final AuditService auditService;
    private final UserService userService;

    private final String CONTROLLER_CATEGORY = "user";

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> userRegist(@RequestBody UserRegist userRegist, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("userRegist")
                .auditDetail(userRegist)
                .build();
        auditService.auditRegist(auditLogRegist);

        userService.userRegist(userRegist);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> userUpdate(@RequestBody UserUpdate userUpdate, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("userUpdate")
                .auditDetail(userUpdate)
                .build();
        auditService.auditRegist(auditLogRegist);

        userService.userUpdate(userUpdate);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update/password")
    public ResponseEntity<ResponseMsg> userUpdatePassword(@RequestBody UserUpdatePassword userUpdatePassword, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("userUpdatePassword")
                .auditDetail(userUpdatePassword)
                .build();
        auditService.auditRegist(auditLogRegist);

        userService.userUpdatePassword(userUpdatePassword);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/active")
    public ResponseEntity<ResponseMsg> userActive(@RequestBody UserActive userActive, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("userActive")
                .auditDetail(userActive)
                .build();
        auditService.auditRegist(auditLogRegist);

        userService.userActive(userActive);
        return ResponseMsg.successResponse("success");
    }

    @DeleteMapping("/delete/{userGuid}")
    public ResponseEntity<ResponseMsg> userDelete(@PathVariable String userGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("userDelete")
                .auditDetail(userGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        userService.userDelete(userGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> userList(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit,
            @RequestParam String companyGuid
    ) {
        return ResponseMsg.successResponse(userService.userListPage(searchType, searchValue, offset, limit, companyGuid));
    }

    @GetMapping("/info/{userGuid}")
    public ResponseEntity<ResponseMsg> userInfo(@PathVariable String userGuid, HttpServletRequest req) {
        AuditLogRegist auditLogRegist = AuditLogRegist.builder()
                .accessToken(req.getHeader("Authorization"))
                .controllerCategory(CONTROLLER_CATEGORY)
                .controllerType("userInfo")
                .auditDetail(userGuid)
                .build();
        auditService.auditRegist(auditLogRegist);

        return ResponseMsg.successResponse(userService.userInfo(userGuid));
    }
}
