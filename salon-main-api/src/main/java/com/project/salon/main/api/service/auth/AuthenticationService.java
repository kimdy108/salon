package com.project.salon.main.api.service.auth;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.domain.report.SalonLoginLog;
import com.project.salon.main.api.dto.admin.AdminAuth;
import com.project.salon.main.api.dto.admin.AdminLogin;
import com.project.salon.main.api.dto.admin.AdminRefresh;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.report.SalonLoginLogRepository;
import com.project.salon.main.api.service.component.RedisService;
import com.project.salon.main.api.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
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
    private final SalonLoginLogRepository salonLoginLogRepository;

    public AdminAuth login (AdminLogin adminLogin) {
        String adminID = decryptStringSalt(adminLogin.getUserID());
        String adminPassword = decryptStringSalt(adminLogin.getUserPassword());
        String companyNumber = decryptStringSalt(adminLogin.getCompanyNumber());
        Long companySeq = 0L;
        LocalDateTime nowDate = LocalDateTime.now();

        if (!companyNumber.equals(MASTER_AUTH_KEY)) {
            SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyNumber(companyNumber);
            if (salonCompany == null) {
                salonLoginLogRepository.save(SalonLoginLog.builder()
                        .loginID(adminID)
                        .loginPassword(adminPassword)
                        .loginResult("noCompany")
                        .adminSeq(0L)
                        .adminGuid(EMPTY_UUID)
                        .insertDate(nowDate)
                        .insertTimestamp(Timestamp.valueOf(nowDate).getTime())
                        .build());
                throw new UsernameNotFoundException("authFail");
            }
            companySeq = salonCompany.getSeq();
        }

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminIDAndCompanySeq(adminID, companySeq);
        if (salonAdmin == null) {
            salonLoginLogRepository.save(SalonLoginLog.builder()
                    .loginID(adminID)
                    .loginPassword(adminPassword)
                    .loginResult("noUser")
                    .adminSeq(0L)
                    .adminGuid(EMPTY_UUID)
                    .insertDate(nowDate)
                    .insertTimestamp(Timestamp.valueOf(nowDate).getTime())
                    .build());
            throw new UsernameNotFoundException("authFail");
        }
        if (IsYesNo.NO.equals(salonAdmin.getIsActive())) {
            salonLoginLogRepository.save(SalonLoginLog.builder()
                    .loginID(adminID)
                    .loginPassword(adminPassword)
                    .loginResult("blockUser")
                    .adminSeq(0L)
                    .adminGuid(EMPTY_UUID)
                    .insertDate(nowDate)
                    .insertTimestamp(Timestamp.valueOf(nowDate).getTime())
                    .build());
            throw new UsernameNotFoundException("authFail");
        }
        if (!passwordEncoder.matches(adminPassword, salonAdmin.getAdminPassword())) {
            salonLoginLogRepository.save(SalonLoginLog.builder()
                    .loginID(adminID)
                    .loginPassword(adminPassword)
                    .loginResult("wrongPassword")
                    .adminSeq(0L)
                    .adminGuid(EMPTY_UUID)
                    .insertDate(nowDate)
                    .insertTimestamp(Timestamp.valueOf(nowDate).getTime())
                    .build());
            throw new UsernameNotFoundException("authFail");
        }

        String accessToken = jwtUtil.createAuthToken(salonAdmin.getAdminName(), salonAdmin.getAdminID(), salonAdmin.getAdminGuid(), salonAdmin.getAdminRole());
        String refreshToken = jwtUtil.createRefreshToken(salonAdmin.getAdminID());

        String accessTokenEnc = encryptStringSalt(accessToken);
        String refreshTokenEnc = encryptStringSalt(refreshToken);

        UUID sessionGuid = UUID.randomUUID();

        redisService.setValues(salonAdmin.getAdminID() + "-" + sessionGuid, refreshTokenEnc);

        salonAdminRepository.updateLastDateByAdminGuid(salonAdmin.getAdminGuid(), LocalDateTime.now());

        salonLoginLogRepository.save(SalonLoginLog.builder()
                .loginID(adminID)
                .loginPassword("")
                .loginResult("success")
                .adminSeq(salonAdmin.getSeq())
                .adminGuid(salonAdmin.getAdminGuid())
                .insertDate(nowDate)
                .insertTimestamp(Timestamp.valueOf(nowDate).getTime())
                .build());

        return AdminAuth.builder()
                .accessToken(accessTokenEnc)
                .refreshToken(refreshTokenEnc)
                .userName(salonAdmin.getAdminName())
                .userGuid(salonAdmin.getAdminGuid())
                .userRole(salonAdmin.getAdminRole())
                .companyGuid(salonAdmin.getCompanyGuid())
                .sessionGuid(sessionGuid)
                .build();
    }

    public String refresh(AdminRefresh adminRefresh) {
        String userAccount = decryptStringSalt(adminRefresh.getUserAccount());
        String refreshToken = decryptStringSalt(adminRefresh.getRefreshToken());

        String redisRefreshToken = redisService.getValues(userAccount);
        if (!redisRefreshToken.equals(refreshToken)) throw new RuntimeException("refreshFail");

        String userID = userAccount.split("-")[0];

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminID(userID);
        if (salonAdmin == null) throw new UsernameNotFoundException("authFail");

        String accessToken = jwtUtil.createAuthToken(salonAdmin.getAdminName(), salonAdmin.getAdminID(), salonAdmin.getAdminGuid(), salonAdmin.getAdminRole());

        return encryptStringSalt(accessToken);
    }
}
