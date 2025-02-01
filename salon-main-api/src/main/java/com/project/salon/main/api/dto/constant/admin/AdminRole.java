package com.project.salon.main.api.dto.constant.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@Accessors(fluent = true)
public enum AdminRole {
    MASTER("최상위 관리자", "ROLE_MASTER"),
    ADMIN("고객사 관리자", "ROLE_ADMIN"),
    MANAGER("고객사 매니저", "ROLE_MANAGER"),
    MEMBER("고객사 운영자", "ROLE_MEMBER");

    private final String title;
    private final String value;

    public static AdminRole of(String value) {
        return Arrays.stream(values())
                .filter(role -> role.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 권한입니다."));
    }
}
