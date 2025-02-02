package com.project.salon.main.api.dto.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@Builder
public class FilterErrorMsg {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private boolean retStatus;
    private int retCode;
    private HttpStatus retHttpStatus;
    private int retHttpCode;
    private Object retData;

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
