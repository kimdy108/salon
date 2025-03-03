package com.project.salon.main.api.domain.report;

import com.project.salon.main.api.domain.common.SalonCommonLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salon_login_log", indexes = {
        @Index(name = "idx_login_id", columnList = "login_id"),
        @Index(name = "idx_user_guid", columnList = "user_guid")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonLoginLog extends SalonCommonLog {
    @Comment("로그인 ID")
    @Column(name = "login_id", nullable = false, length = 50)
    private String loginID;

    @Comment("로그인 패스워드(암호화)")
    @Column(name = "login_password", length = 200)
    private String loginPassword;

    @Comment("로그인 결과")
    @Column(name = "login_result", nullable = false, length = 20)
    private String loginResult;

    @Comment("사용자 SEQ(성공시)")
    @Column(name = "admin_seq", length=20)
    private Long adminSeq;

    @Comment("사용자 GUID(성공시)")
    @Column(name = "admin_guid", length=50)
    private UUID adminGuid;

    @Builder
    public SalonLoginLog(String loginID, String loginPassword, String loginResult, Long adminSeq, UUID adminGuid, LocalDateTime insertDate, Long insertTimestamp) {
        this.loginID = loginID;
        this.loginPassword = loginPassword;
        this.loginResult = loginResult;
        this.adminSeq = adminSeq;
        this.adminGuid = adminGuid;

        this.setInsertDate(insertDate);
        this.setInsertTimestamp(insertTimestamp);
    }
}
