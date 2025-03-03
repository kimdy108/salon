package com.project.salon.main.api.controller.system;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.system.InstallSalon;
import com.project.salon.main.api.service.system.InstallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/setting/install")
public class InstallController {
    private final InstallService installService;

    @GetMapping("/check")
    public ResponseEntity<ResponseMsg> installCheck() {
        return ResponseMsg.successResponse(installService.installCheck());
    }

    @PostMapping("/salon")
    public ResponseEntity<ResponseMsg> installSalon(@RequestBody InstallSalon installSalon) {
        installService.installSalon(installSalon);
        return ResponseMsg.successResponse("success");
    }
}
