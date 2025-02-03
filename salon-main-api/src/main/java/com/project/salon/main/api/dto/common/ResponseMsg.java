package com.project.salon.main.api.dto.common;

import com.project.salon.main.api.utils.Common;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ResponseMsg {
    private boolean retStatus;
    private int retCode;
    private HttpStatus retHttpStatus;
    private int retHttpCode;
    private Object retData;

    public static ResponseEntity<ResponseMsg> successResponse(Object data) {
        ResponseMsg responseMsg = ResponseMsg.builder()
                .retStatus(true)
                .retCode(Common.StatusCode.RETURN_SUCCESS)
                .retHttpCode(HttpStatus.ACCEPTED.value())
                .retHttpStatus(HttpStatus.ACCEPTED)
                .retData(data)
                .build();

        return ResponseEntity.ok().body(responseMsg);
    }

    public static ResponseEntity<ResponseMsg> errorResponse(Object data) {
        ResponseMsg responseMsg = ResponseMsg.builder()
                .retStatus(false)
                .retCode(Common.StatusCode.RETURN_ERROR)
                .retHttpStatus(HttpStatus.BAD_REQUEST)
                .retHttpCode(HttpStatus.BAD_REQUEST.value())
                .retData(data)
                .build();

        return ResponseEntity.badRequest().body(responseMsg);
    }

    public static ResponseEntity<ResponseMsg> authFailResponse(Object data) {
        ResponseMsg responseMsg = ResponseMsg.builder()
                .retStatus(false)
                .retCode(Common.StatusCode.RETURN_AUTHFAIL)
                .retHttpStatus(HttpStatus.FORBIDDEN)
                .retHttpCode(HttpStatus.FORBIDDEN.value())
                .retData(data)
                .build();

        return ResponseEntity.badRequest().body(responseMsg);
    }

////////// security filter exception response //////////

    public static ResponseMsg noTokenResponse(Object data) {
        return ResponseMsg.builder()
                .retStatus(false)
                .retCode(Common.StatusCode.RETURN_NOTOKEN)
                .retHttpStatus(HttpStatus.UNAUTHORIZED)
                .retHttpCode(HttpStatus.UNAUTHORIZED.value())
                .retData(data)
                .build();
    }

    public static ResponseMsg expiredTokenResponse(Object data) {
        return ResponseMsg.builder()
                .retStatus(false)
                .retCode(Common.StatusCode.RETURN_EXPIRE)
                .retHttpStatus(HttpStatus.PRECONDITION_FAILED)
                .retHttpCode(HttpStatus.PRECONDITION_FAILED.value())
                .retData(data)
                .build();
    }
}