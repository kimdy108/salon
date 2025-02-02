package com.project.salon.main.api.controller.setting;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.service.setting.InstallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/setting/install")
public class InstallController {
    private final InstallService installService;

    @GetMapping("/check")
    public ResponseEntity<ResponseMsg> installCheck() {
        return ResponseMsg.successResponse(installService.installCheck());
    }
}
