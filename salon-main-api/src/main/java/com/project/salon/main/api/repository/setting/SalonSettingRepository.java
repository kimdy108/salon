package com.project.salon.main.api.repository.setting;

import com.project.salon.main.api.domain.setting.SalonSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonSettingRepository extends JpaRepository<SalonSetting, Long> {
    SalonSetting findSalonSettingBySettingKey(String settingKey);
}
