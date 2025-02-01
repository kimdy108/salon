package com.project.salon.main.api.domain.company;

import com.project.salon.main.api.domain.common.SalonCommonBase;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "salon_company", indexes = {
        @Index(name = "idx_company_guid", columnList = "company_guid"),
        @Index(name = "idx_company_name", columnList = "company_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalonCompany extends SalonCommonBase {
    @Comment("고객사 UUID")
    @Column(name = "company_guid", unique = true, length = 50, updatable = false, nullable = false)
    private UUID companyGuid;

    @Comment("고객사 사업자 번호")
    @Column(name = "company_number", unique = true, length = 10, updatable = false, nullable = false)
    private String companyNumber;

    @Comment("고객사 이름")
    @Column(name = "company_name", length = 50, nullable = false)
    private String companyName;

    @Comment("고객사 주소")
    @Column(name = "company_address", length = 100, nullable = false)
    private String companyAddress;

    @Comment("고객사 전화번호")
    @Column(name = "company_phone", length = 20, nullable = false)
    private String companyPhone;

    @Builder
    public SalonCompany(UUID companyGuid, String companyNumber, String companyName, String companyAddress, String companyPhone, IsYesNo isActive, LocalDateTime insertDate, LocalDateTime updateDate, String descriptionNote) {
        this.companyGuid = companyGuid;
        this.companyNumber = companyNumber;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;

        this.setIsActive(isActive);
        this.setInsertDate(insertDate);
        this.setUpdateDate(updateDate);
        this.setDescriptionNote(descriptionNote);
    }
}
