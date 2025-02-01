package com.project.salon.main.api.repository.admin;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalonAdminRepository extends JpaRepository<SalonAdmin, Long> {
    SalonAdmin findSalonAdminByAdminId(String adminId);
}
