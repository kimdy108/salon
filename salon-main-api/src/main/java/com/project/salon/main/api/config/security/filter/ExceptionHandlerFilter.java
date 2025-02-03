package com.project.salon.main.api.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.salon.main.api.dto.common.ResponseMsg;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, ResponseMsg.noTokenResponse("noToken"));
        } catch (AccountExpiredException e) {
            setErrorResponse(response, HttpStatus.PRECONDITION_FAILED, ResponseMsg.expiredTokenResponse("expiredToken"));
        }
    }

    public void setErrorResponse(HttpServletResponse response, HttpStatus httpStatus, ResponseMsg responseMsg) throws IOException {

        response.setStatus(httpStatus.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseMsg));
    }
}
