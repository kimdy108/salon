package com.project.salon.main.api.controller.auth;

import com.project.salon.main.api.dto.admin.AdminAuth;
import com.project.salon.main.api.dto.admin.AdminLogin;
import com.project.salon.main.api.dto.admin.AdminRefresh;
import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.salon.main.api.utils.Common.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseMsg> login (@RequestBody AdminLogin adminLogin, HttpServletResponse res) {
        AdminAuth adminAuth = authenticationService.login(adminLogin);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.setHeader("accesstoken", adminAuth.getAccessToken());
        res.setHeader("refreshtoken", adminAuth.getRefreshToken());

        String result = encryptString(
                adminAuth.getUserName() + AUTHCHAR +
                adminAuth.getUserGuid() + AUTHCHAR +
                adminAuth.getUserRole() + AUTHCHAR +
                adminAuth.getCompanyGuid() + AUTHCHAR +
                adminAuth.getSessionGuid()
        );

        return ResponseMsg.successResponse(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseMsg> refresh (@RequestBody AdminRefresh adminRefresh, HttpServletResponse res) {
        res.setHeader("accessToken", authenticationService.refresh(adminRefresh));

        return ResponseMsg.successResponse("success");
    }
}
