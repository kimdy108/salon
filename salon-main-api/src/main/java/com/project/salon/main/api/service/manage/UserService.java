package com.project.salon.main.api.service.manage;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.manage.user.*;
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

import static com.project.salon.main.api.utils.Common.*;

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
        if (salonCompany == null) throw new RuntimeException("존재하지 않는 고객사 입니다.");

        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminIDAndCompanySeq(decryptStringSalt(userRegist.getUserID()), salonCompany.getSeq());
        if (salonAdmin != null) throw new RuntimeException("존재하는 사용자 입니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        salonAdminRepository.save(SalonAdmin.builder()
                .adminGuid(UUID.randomUUID())
                .adminID(decryptStringSalt(userRegist.getUserID()))
                .adminPassword(passwordEncoder.encode(decryptStringSalt(userRegist.getUserPassword())))
                .companyGuid(salonCompany.getCompanyGuid())
                .companySeq(salonCompany.getSeq())
                .adminName(decryptStringSalt(userRegist.getUserName()))
                .adminPhone(decryptStringSalt(userRegist.getUserPhone()))
                .adminEmail(decryptStringSalt(userRegist.getUserEmail()))
                .adminRole(userRegist.getUserRole())
                .adminType("NORMAL")
                .lastDate(LocalDateTime.parse("2000-01-01T00:00:00"))
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote(userRegist.getDescriptionNote())
                .build());
    }

    @Transactional
    public void userUpdate(UserUpdate userUpdate) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(userUpdate.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdmin.update(
                decryptStringSalt(userUpdate.getUserName()),
                decryptStringSalt(userUpdate.getUserPhone()),
                decryptStringSalt(userUpdate.getUserEmail()),
                userUpdate.getUserRole(),
                LocalDateTime.now(),
                userUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void userUpdatePassword(UserUpdatePassword userUpdatePassword) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(userUpdatePassword.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdmin.updatePassword(
                passwordEncoder.encode(decryptStringSalt(userUpdatePassword.getUserPassword())),
                LocalDateTime.now()
        );
    }

    @Transactional
    public void userActive(UserActive userActive) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(userActive.getUserGuid());
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdminRepository.updateSalonAdminByIsActiveStatus(userActive.getUserGuid(), userActive.getIsActive(), LocalDateTime.now());
    }

    @Transactional
    public void userDelete(String userGuid) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(UUID.fromString(userGuid));
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdminRepository.delete(salonAdmin);
    }

    public Page<UserList> userListPage(String searchType, String searchValue, Long offset, int limit, String companyGuid) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return salonAdminRepositoryImpl.findUserListPage(searchType, searchValue, offset, limit, pageable, UUID.fromString(companyGuid));
    }

    public UserInfo userInfo(String userGuid) {
        UserInfo userInfo = salonAdminRepositoryImpl.findUserInfo(UUID.fromString(userGuid));

        userInfo.setUserID(encryptStringSalt(userInfo.getUserID()));
        userInfo.setUserName(encryptStringSalt(userInfo.getUserName()));
        userInfo.setUserPhone(encryptStringSalt(userInfo.getUserPhone()));
        userInfo.setUserEmail(encryptStringSalt(userInfo.getUserEmail()));

        return userInfo;
    }
}
