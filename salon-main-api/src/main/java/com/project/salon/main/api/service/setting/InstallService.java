package com.project.salon.main.api.service.setting;

import com.project.salon.main.api.domain.setting.SalonSetting;
import com.project.salon.main.api.repository.setting.SalonSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstallService {
    private final SalonSettingRepository salonSettingRepository;

    public boolean installCheck() {
        boolean result = false;

        SalonSetting salonSetting = salonSettingRepository.findSalonSettingBySettingKey("INSTALL");
        if (salonSetting != null && Integer.parseInt(salonSetting.getSettingVal()) > 0) result = true;

        return result;
    }
}
