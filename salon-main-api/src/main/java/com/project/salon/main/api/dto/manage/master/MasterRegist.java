package com.project.salon.main.api.dto.manage.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterRegist {
    private String masterID;
    private String masterPassword;
    private String masterName;
    private String masterPhone;
    private String masterEmail;
    private String descriptionNote;
}
