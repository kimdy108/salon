package com.project.salon.main.api.config.security.filter;

import com.project.salon.main.api.dto.common.FilterErrorMsg;
import com.project.salon.main.api.utils.Common;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            setErrorResponse(response,
                    Common.StatusCode.RETURN_NOTOKEN,
                    HttpStatus.UNAUTHORIZED,
                    "noToken");
        } catch (AccountExpiredException e) {
            setErrorResponse(response,
                    Common.StatusCode.RETURN_EXPIRE,
                    HttpStatus.PRECONDITION_FAILED,
                    "expiredToken");
        }
    }

    public void setErrorResponse(HttpServletResponse response, int retCode, HttpStatus retHttpStatus, String retData) throws IOException {

        response.setStatus(retHttpStatus.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(
                FilterErrorMsg.builder()
                        .retStatus(false)
                        .retCode(retCode)
                        .retHttpStatus(retHttpStatus)
                        .retHttpCode(retHttpStatus.value())
                        .retData(retData)
                        .build().convertToJson()
        );
    }
}
