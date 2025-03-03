package com.project.salon.main.api.domain.system;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salon_setting", indexes = {
        @Index(name = "idx_setting_key", columnList = "setting_key")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonSetting {
    @Id
    @Column(name = "setting_key", nullable = false, length = 50)
    private String settingKey;

    @Column(name = "setting_val", columnDefinition = "TEXT")
    private String settingVal;

    @Builder
    public SalonSetting(String settingKey, String settingVal) {
        this.settingKey = settingKey;
        this.settingVal = settingVal;
    }
}
