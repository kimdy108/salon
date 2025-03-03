package com.project.salon.main.api.dto.report.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLogList {
    private String loginResult;
    private String loginID;
    private UUID userGuid;
    private LocalDateTime insertDate;

    private String userName;
    private String companyNumber;
    private String companyName;
}
