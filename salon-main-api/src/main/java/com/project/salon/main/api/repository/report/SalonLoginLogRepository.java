package com.project.salon.main.api.repository.report;

import com.project.salon.main.api.domain.report.SalonLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonLoginLogRepository extends JpaRepository<SalonLoginLog, Long> {
}
