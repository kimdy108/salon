package com.project.salon.main.api.dto.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ResponseMsg {
    public static final String SUCCESS = "1000";
    public static final String ERROR = "9999";

    private boolean retStatus;
    private int retCode;
    private HttpStatus retHttpStatus;
    private int retHttpCode;
    private Object retData;
}