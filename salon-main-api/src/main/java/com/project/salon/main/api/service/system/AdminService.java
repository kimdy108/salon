package com.project.salon.main.api.service.system;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.dto.admin.AdminInfo;
import com.project.salon.main.api.dto.admin.AdminUpdate;
import com.project.salon.main.api.dto.admin.AdminUpdatePassword;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.admin.SalonAdminRepositoyImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.*;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final SalonAdminRepository salonAdminRepository;
    private final SalonAdminRepositoyImpl salonAdminRepositoyImpl;

    @Transactional
    public void adminUpdate(AdminUpdate adminUpdate) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(adminUpdate.getAdminGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");

        salonAdmin.update(
                decryptStringSalt(adminUpdate.getAdminName()),
                decryptStringSalt(adminUpdate.getAdminPhone()),
                decryptStringSalt(adminUpdate.getAdminEmail()),
                adminUpdate.getAdminRole(),
                LocalDateTime.now(),
                adminUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void adminUpdatePassword(AdminUpdatePassword adminUpdatePassword) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(adminUpdatePassword.getAdminGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");
        if (!passwordEncoder.matches(decryptStringSalt(adminUpdatePassword.getOldAdminPassword()), salonAdmin.getAdminPassword())) throw new RuntimeException("이전 비밀번호가 일치하지 않습니다.");

        salonAdmin.updatePassword(
                passwordEncoder.encode(decryptStringSalt(adminUpdatePassword.getNewAdminPassword())),
                LocalDateTime.now()
        );
    }

    public AdminInfo adminInfo(String adminGuid) {
        AdminInfo adminInfo = salonAdminRepositoyImpl.findAdminInfo(UUID.fromString(adminGuid));

        adminInfo.setAdminID(encryptStringSalt(adminInfo.getAdminID()));
        adminInfo.setAdminName(encryptStringSalt(adminInfo.getAdminName()));
        adminInfo.setAdminPhone(encryptStringSalt(adminInfo.getAdminPhone()));
        adminInfo.setAdminEmail(encryptStringSalt(adminInfo.getAdminEmail()));

        return adminInfo;
    }
}
