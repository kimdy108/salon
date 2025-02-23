package com.project.salon.main.api.service.manage;

import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.manage.company.*;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import com.project.salon.main.api.repository.manage.SalonCompanyRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final SalonCompanyRepository salonCompanyRepository;
    private final SalonCompanyRepositoryImpl salonCompanyRepositoryImpl;

    @Transactional
    public void companyRegist(CompanyRegist companyRegist) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyNumber(companyRegist.getCompanyNumber());
        if (salonCompany != null) throw new RuntimeException("existCompany");

        LocalDateTime nowDate = LocalDateTime.now();

        salonCompanyRepository.save(SalonCompany.builder()
                .companyGuid(UUID.randomUUID())
                .companyNumber(companyRegist.getCompanyNumber())
                .companyName(companyRegist.getCompanyName())
                .companyAddress(companyRegist.getCompanyAddress())
                .managerName(companyRegist.getManagerName())
                .managerPhone(companyRegist.getManagerPhone())
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote(companyRegist.getDescriptionNote())
                .build());
    }

    @Transactional
    public void companyUpdate(CompanyUpdate companyUpdate) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(companyUpdate.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("noCompany");

        salonCompany.update(
                companyUpdate.getCompanyName(),
                companyUpdate.getCompanyAddress(),
                companyUpdate.getManagerName(),
                companyUpdate.getManagerPhone(),
                LocalDateTime.now(),
                companyUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void companyActive(CompanyActive companyActive) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(companyActive.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("noCompany");

        salonCompanyRepository.updateSalonCompanyByActiveStatus(companyActive.getCompanyGuid(), companyActive.getIsActive(), LocalDateTime.now());
    }

    @Transactional
    public void companyDelete(String companyGuid) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(UUID.fromString(companyGuid));
        if (salonCompany == null) throw new RuntimeException("noCompany");

        salonCompanyRepository.delete(salonCompany);
    }

    public Page<CompanyList> companyListPage(String searchType, String searchValue, Long offset, int limit) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return salonCompanyRepositoryImpl.findCompanyListPage(searchType, searchValue, offset, limit, pageable);
    }

    public CompanyInfo getCompanyInfo(String companyGuid) {
        return salonCompanyRepositoryImpl.findCompanyInfo(UUID.fromString(companyGuid));
    }

    public List<CompanyListAll> getCompanyListAll() {
        return salonCompanyRepositoryImpl.findCompanyListAll();
    }
}
