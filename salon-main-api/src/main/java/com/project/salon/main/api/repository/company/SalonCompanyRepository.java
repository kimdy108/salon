package com.project.salon.main.api.repository.company;

import com.project.salon.main.api.domain.company.SalonCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalonCompanyRepository extends JpaRepository<SalonCompany, Long> {
    SalonCompany findSalonCompanyByCompanyNumber(String companyNumber);
}
