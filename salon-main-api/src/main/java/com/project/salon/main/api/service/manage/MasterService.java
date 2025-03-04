package com.project.salon.main.api.service.manage;

import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.manage.master.*;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.admin.SalonAdminRepositoyImpl;
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
public class MasterService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final SalonAdminRepository salonAdminRepository;

    private final SalonAdminRepositoyImpl salonAdminRepositoryImpl;

    @Transactional
    public void masterRegist(MasterRegist masterRegist) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminIDAndCompanySeq(decryptStringSalt(masterRegist.getMasterID()), 0L);
        if (salonAdmin != null) throw new RuntimeException("존재하는 사용자 입니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        salonAdminRepository.save(SalonAdmin.builder()
                .adminGuid(UUID.randomUUID())
                .adminID(decryptStringSalt(masterRegist.getMasterID()))
                .adminPassword(passwordEncoder.encode(decryptStringSalt(masterRegist.getMasterPassword())))
                .companyGuid(EMPTY_UUID)
                .companySeq(0L)
                .adminName(decryptStringSalt(masterRegist.getMasterName()))
                .adminPhone(decryptStringSalt(masterRegist.getMasterPhone()))
                .adminEmail(decryptStringSalt(masterRegist.getMasterEmail()))
                .adminRole(AdminRole.MASTER)
                .adminType("NORMAL")
                .lastDate(LocalDateTime.parse("2000-01-01T00:00:00"))
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .descriptionNote(masterRegist.getDescriptionNote())
                .build());
    }

    @Transactional
    public void masterUpdate(MasterUpdate masterUpdate) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(masterUpdate.getMasterGuid());
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdmin.update(
                decryptStringSalt(masterUpdate.getMasterName()),
                decryptStringSalt(masterUpdate.getMasterPhone()),
                decryptStringSalt(masterUpdate.getMasterEmail()),
                AdminRole.MASTER,
                LocalDateTime.now(),
                masterUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void masterUpdatePassword(MasterUpdatePassword masterUpdatePassword) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(masterUpdatePassword.getMasterGuid());
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdmin.updatePassword(
                passwordEncoder.encode(decryptStringSalt(masterUpdatePassword.getMasterPassword())),
                LocalDateTime.now()
        );
    }

    @Transactional
    public void masterActive(MasterActive masterActive) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(masterActive.getMasterGuid());
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdminRepository.updateSalonAdminByIsActiveStatus(masterActive.getMasterGuid(), masterActive.getIsActive(), LocalDateTime.now());
    }

    @Transactional
    public void masterDelete(String masterGuid) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(UUID.fromString(masterGuid));
        if (salonAdmin == null) throw new RuntimeException("존재하지 않는 사용자 입니다.");

        salonAdminRepository.delete(salonAdmin);
    }

    public Page<MasterList> masterListPage(String searchType, String searchValue, Long offset, int limit) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return salonAdminRepositoryImpl.findMasterListPage(searchType, searchValue, offset, limit, pageable);
    }

    public MasterInfo masterInfo(String masterGuid) {
        MasterInfo masterInfo = salonAdminRepositoryImpl.findMasterInfo(UUID.fromString(masterGuid));

        masterInfo.setMasterID(encryptStringSalt(masterInfo.getMasterID()));
        masterInfo.setMasterName(encryptStringSalt(masterInfo.getMasterName()));
        masterInfo.setMasterPhone(encryptStringSalt(masterInfo.getMasterPhone()));
        masterInfo.setMasterEmail(encryptStringSalt(masterInfo.getMasterEmail()));

        return masterInfo;
    }
}
