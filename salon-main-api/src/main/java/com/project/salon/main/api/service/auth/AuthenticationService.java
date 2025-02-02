package com.project.salon.main.api.service.auth;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.company.SalonCompany;
import com.project.salon.main.api.dto.admin.AdminAuth;
import com.project.salon.main.api.dto.admin.AdminLogin;
import com.project.salon.main.api.dto.admin.AdminRefresh;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.company.SalonCompanyRepository;
import com.project.salon.main.api.service.component.RedisService;
import com.project.salon.main.api.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    private final SalonAdminRepository salonAdminRepository;
    private final SalonCompanyRepository salonCompanyRepository;

    public AdminAuth login (AdminLogin adminLogin) {
        String adminID = adminLogin.getUserID();
        String adminPassword = adminLogin.getUserPassword();
        String companyNumber = adminLogin.getCompanyNumber();

//        String adminID = decryptStringSalt(adminLogin.getUserID());
//        String adminPassword = decryptStringSalt(adminLogin.getUserPassword());
//        String companyNumber = decryptStringSalt(adminLogin.getCompanyNumber());

        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyNumber(companyNumber);
        if (salonCompany == null) throw new UsernameNotFoundException("authFail");

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminIDAndCompanySeq(adminID, salonCompany.getSeq());
        if (salonAdmin == null) throw new UsernameNotFoundException("authFail");
        if (IsYesNo.NO.equals(salonAdmin.getIsActive())) throw new UsernameNotFoundException("authFail");
        if (!passwordEncoder.matches(adminPassword, salonAdmin.getAdminPassword())) throw new UsernameNotFoundException("authFail");

        String accessToken = jwtUtil.createAuthToken(salonAdmin.getAdminName(), salonAdmin.getAdminID(), salonAdmin.getAdminRole());
        String refreshToken = jwtUtil.createRefreshToken(salonAdmin.getAdminName(), salonAdmin.getAdminID(), salonAdmin.getAdminRole());

        String accessTokenEnc = encryptStringSalt(accessToken);
        String refreshTokenEnc = encryptStringSalt(refreshToken);

        UUID sessionGuid = UUID.randomUUID();

        redisService.setValues(salonAdmin.getAdminID() + "-" + sessionGuid, refreshTokenEnc);

        salonAdminRepository.updateLastDateByAdminGuid(salonAdmin.getAdminGuid(), LocalDateTime.now());

        return AdminAuth.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenEnc)
                .userName(salonAdmin.getAdminName())
                .userGuid(salonAdmin.getAdminGuid())
                .userRole(salonAdmin.getAdminRole())
                .companyGuid(salonCompany.getCompanyGuid())
                .sessionGuid(sessionGuid)
                .build();
    }

    public String refresh(AdminRefresh adminRefresh) {
        String userAccount = adminRefresh.getUserAccount();
        String refreshToken = adminRefresh.getRefreshToken();

//        String userAccount = decryptStringSalt(adminRefresh.getUserAccount());
//        String refreshToken = decryptStringSalt(adminRefresh.getRefreshToken());

        String redisRefreshToken = redisService.getValues(userAccount);
        if (!redisRefreshToken.equals(refreshToken)) throw new RuntimeException("refreshFail");

        String userID = userAccount.split("-")[0];

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminID(userID);
        if (salonAdmin == null) throw new UsernameNotFoundException("authFail");

        String accessToken = jwtUtil.createAuthToken(salonAdmin.getAdminName(), salonAdmin.getAdminID(), salonAdmin.getAdminRole());

        return encryptStringSalt(accessToken);
    }
}
