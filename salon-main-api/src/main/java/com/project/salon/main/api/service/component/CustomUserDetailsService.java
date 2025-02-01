package com.project.salon.main.api.service.component;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.dto.component.CustomUserDetails;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SalonAdminRepository salonAdminRepository;

    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminId(adminId);

        if (salonAdmin != null) {

            return new CustomUserDetails(salonAdmin);
        }

        return null;
    }
}
