package com.project.salon.main.api.config.security;

import com.project.salon.main.api.config.security.filter.ExceptionHandlerFilter;
import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.config.security.filter.JWTFilter;
import com.project.salon.main.api.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JWTUtil jwtUtil;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    private final String JOIN_API = "/api/auth/join";
    private final String LOGIN_API = "/api/auth/login";
    private final String REFRESH_API = "/api/auth/refresh";
    private final String INSTALL_API = "/api/main/setting/install/**";
    private final String MASTER_API = "/api/master/**";
    private final String ADMIN_API = "/api/admin/**";
    private final String MANAGER_API = "/api/manager/**";
    private final String MAIN_API = "/api/main/**";

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring().requestMatchers(
                JOIN_API, LOGIN_API, REFRESH_API, INSTALL_API
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .authorizeHttpRequests(this::authorizeHttpRequests)
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JWTFilter.class)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    private void authorizeHttpRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer) {
        configurer
                .requestMatchers(MASTER_API).hasAnyRole(AdminRole.MASTER.name())
                .requestMatchers(ADMIN_API).hasAnyRole(AdminRole.MEMBER.name(), AdminRole.ADMIN.name())
                .requestMatchers(MANAGER_API).hasAnyRole(AdminRole.MASTER.name(), AdminRole.ADMIN.name(), AdminRole.MANAGER.name())
                .requestMatchers(MAIN_API).hasAnyRole(AdminRole.MASTER.name(), AdminRole.ADMIN.name(), AdminRole.MANAGER.name(), AdminRole.MEMBER.name());
    }
}
