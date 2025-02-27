package com.project.salon.main.api.service.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.salon.main.api.domain.report.SalonAuditLog;
import com.project.salon.main.api.dto.common.AuditLogRegist;
import com.project.salon.main.api.repository.report.SalonAuditLogRepository;
import com.project.salon.main.api.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.EMPTY_UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {
    private final JWTUtil jwtUtil;
    private final LoggerService loggerService;

    private final SalonAuditLogRepository salonAuditLogRepository;

    public void auditRegist(AuditLogRegist auditLogRegist) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            salonAuditLogRepository.save(SalonAuditLog.builder()
                    .controllerType(auditLogRegist.getControllerType())
                    .controllerCategory(auditLogRegist.getControllerCategory())
                    .userGuid(UUID.fromString(String.valueOf(jwtUtil.decodeAccessToken(auditLogRegist.getAccessToken()).get("userGuid"))))
                    .auditDetail(objectMapper.writeValueAsString(auditLogRegist.getAuditDetail()))
                    .insertDate(LocalDateTime.now())
                    .build()
            );
        } catch (Exception ex1) {
            try {
                salonAuditLogRepository.save(SalonAuditLog.builder()
                        .controllerType(auditLogRegist.getControllerType())
                        .controllerCategory(auditLogRegist.getControllerCategory())
                        .userGuid(EMPTY_UUID)
                        .auditDetail(objectMapper.writeValueAsString(auditLogRegist.getAuditDetail()))
                        .insertDate(LocalDateTime.now())
                        .build()
                );
            } catch (Exception ex2) {
                loggerService.writeLogger("ERROR", "Audit Log Error : " + ex2.getMessage());
            }
        }
    }
}
