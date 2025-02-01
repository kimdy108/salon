package com.project.salon.main.api.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class SalonCommonBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Comment("활성화 / 비활성화")
    @Column(name = "is_active", columnDefinition = "ENUM('YES','NO') NOT NULL DEFAULT 'YES'")
    @Enumerated(EnumType.STRING)
    private IsYesNo isActive;

    @Comment("등록날짜")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @CreationTimestamp
    @Column(name = "insert_date", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime insertDate;

    @Comment("수정날짜")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name = "update_date", columnDefinition="DATETIME")
    private LocalDateTime updateDate;

    @Comment("비고")
    @Column(name = "description_note", columnDefinition = "TEXT")
    private String descriptionNote;
}
