package com.project.salon.main.api.dto.dashboard.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCurrent {
    private int allUser;
    private int addUser;
    private int activeUser;
}
