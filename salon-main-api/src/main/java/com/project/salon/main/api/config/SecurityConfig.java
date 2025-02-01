package com.project.salon.main.api.config;

import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.utils.jwt.JWTFilter;
import com.project.salon.main.api.utils.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final String JOIN_API = "/api/auth/join";
    private final String LOGIN_API = "/api/auth/login";
    private final String REFRESH_API = "/api/auth/refresh";
    private final String MASTER_API = "/api/master/**";
    private final String ADMIN_API = "/api/admin/**";
    private final String MANAGER_API = "/api/manager/**";
    private final String MAIN_API = "/api/main/**";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(JOIN_API, LOGIN_API, REFRESH_API).permitAll()
                        .requestMatchers(MASTER_API).hasAnyRole(AdminRole.MASTER.name())
                        .requestMatchers(ADMIN_API).hasAnyRole(AdminRole.MEMBER.name(), AdminRole.ADMIN.name())
                        .requestMatchers(MANAGER_API).hasAnyRole(AdminRole.MASTER.name(), AdminRole.ADMIN.name(), AdminRole.MANAGER.name())
                        .requestMatchers(MAIN_API).hasAnyRole(AdminRole.MASTER.name(), AdminRole.ADMIN.name(), AdminRole.MANAGER.name(), AdminRole.MEMBER.name())
                        .anyRequest().authenticated());

        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("accesstoken", "refreshtoken"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
