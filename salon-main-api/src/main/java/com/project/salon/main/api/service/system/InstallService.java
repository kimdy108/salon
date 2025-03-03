package com.project.salon.main.api.service.system;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.system.SalonSetting;
import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.system.InstallSalon;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.system.SalonSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.*;

@Service
@RequiredArgsConstructor
public class InstallService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final SalonSettingRepository salonSettingRepository;
    private final SalonAdminRepository salonAdminRepository;

    public boolean installCheck() {
        boolean result = false;

        SalonSetting salonSetting = salonSettingRepository.findSalonSettingBySettingKey("INSTALL");
        if (salonSetting != null && Integer.parseInt(salonSetting.getSettingVal()) > 0) result = true;

        return result;
    }

    public void installSalon(InstallSalon installSalon) {
        if (!INSTALL_AUTH_KEY.equals(installSalon.getAuthKey())) throw new RuntimeException("인증키를 확인해주세요.");

        LocalDateTime nowDate = LocalDateTime.now();

        SalonAdmin salonAdmin = SalonAdmin.builder()
                .adminGuid(UUID.randomUUID())
                .adminID(decryptStringSalt(installSalon.getAdminID()))
                .adminPassword(passwordEncoder.encode(decryptStringSalt(installSalon.getAdminPassword())))
                .companyGuid(EMPTY_UUID)
                .companySeq(EMPTY_SEQ)
                .adminName(decryptStringSalt(installSalon.getAdminName()))
                .adminPhone(decryptStringSalt(installSalon.getAdminPhone()))
                .adminEmail(decryptStringSalt(installSalon.getAdminEmail()))
                .adminRole(AdminRole.MASTER)
                .adminType("SYSTEM")
                .lastDate(LocalDateTime.parse("2000-01-29T00:00:00"))
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote("")
                .build();
        salonAdminRepository.save(salonAdmin);

        SalonSetting salonSetting = SalonSetting.builder()
                .settingKey("INSTALL")
                .settingVal("1")
                .build();
        salonSettingRepository.save(salonSetting);
    }
}
