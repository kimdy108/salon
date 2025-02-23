package com.project.salon.main.api.service.manage;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.manage.user.UserActive;
import com.project.salon.main.api.dto.manage.user.UserList;
import com.project.salon.main.api.dto.manage.user.UserRegist;
import com.project.salon.main.api.dto.manage.user.UserUpdate;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.admin.SalonAdminRepositoyImpl;
import com.project.salon.main.api.repository.manage.SalonCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final SalonAdminRepository salonAdminRepository;
    private final SalonCompanyRepository salonCompanyRepository;

    private final SalonAdminRepositoyImpl salonAdminRepositoryImpl;

    @Transactional
    public void userRegist(UserRegist userRegist) {
        SalonCompany salonCompany = salonCompanyRepository.findSalonCompanyByCompanyGuid(userRegist.getCompanyGuid());
        if (salonCompany == null) throw new RuntimeException("noCompany");

        LocalDateTime nowDate = LocalDateTime.now();

        salonAdminRepository.save(SalonAdmin.builder()
                .adminID(userRegist.getUserID())
                .adminPassword(passwordEncoder.encode(userRegist.getUserPassword()))
                .companyGuid(userRegist.getCompanyGuid())
                .companySeq(userRegist.getCompanySeq())
                .adminName(userRegist.getUserName())
                .adminPhone(userRegist.getUserPhone())
                .adminEmail(userRegist.getUserEmail())
                .adminRole(userRegist.getUserRole())
                .adminType("NORMAL")
                .lastDate(LocalDateTime.parse("2000-01-01 00:00:00"))
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote(userRegist.getDescriptionNote())
                .build());
    }

    @Transactional
    public void userUpdate(UserUpdate userUpdate) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(userUpdate.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("noAdmin");

        salonAdmin.update(
                userUpdate.getUserName(),
                userUpdate.getUserPhone(),
                userUpdate.getUserEmail(),
                userUpdate.getUserRole(),
                LocalDateTime.now(),
                userUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void userActive(UserActive userActive) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(userActive.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("noAdmin");

        salonAdminRepository.updateSalonAdminByIsActiveStatus(userActive.getUserGuid(), userActive.getIsActive(), LocalDateTime.now());
    }

    @Transactional
    public void userDelete(String userGuid) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(UUID.fromString(userGuid));
        if (salonAdmin == null) throw new RuntimeException("noAdmin");

        salonAdminRepository.delete(salonAdmin);
    }

    public Page<UserList> userListPage(String searchType, String searchValue, Long offset, int limit) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return salonAdminRepositoryImpl.findUserListPage(searchType, searchValue, offset, limit, pageable);
    }
}
