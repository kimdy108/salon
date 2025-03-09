package com.project.salon.main.api.service.setting;

import com.google.gson.Gson;
import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.setting.SalonStyle;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.setting.style.*;
import com.project.salon.main.api.repository.admin.SalonAdminRepository;
import com.project.salon.main.api.repository.setting.SalonStyleRepository;
import com.project.salon.main.api.repository.setting.SalonStyleRepositoryImpl;
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

import static com.project.salon.main.api.utils.Common.*;

@Service
@RequiredArgsConstructor
public class StyleService {
    private final Gson gson;

    private final SalonAdminRepository salonAdminRepository;
    private final SalonStyleRepository salonStyleRepository;

    private final SalonStyleRepositoryImpl salonStyleRepositoryImpl;

    @Transactional
    public void styleRegist(StyleRegist styleRegist) {
        SalonAdmin salonAdmin = salonAdminRepository.findSalonAdminByAdminGuid(styleRegist.getAdminGuid());
        if (salonAdmin == null) throw new RuntimeException("사용자가 존재하지 않습니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        salonStyleRepository.save(SalonStyle.builder()
                .styleGuid(UUID.randomUUID())
                .styleName(styleRegist.getStyleName())
                .adminSeq(salonAdmin.getSeq())
                .adminGuid(salonAdmin.getAdminGuid())
                .styleDetail(styleRegist.getStyleDetail())
                .styleDuration(styleRegist.getStyleDuration())
                .isMiddleTime(styleRegist.getIsMiddleTime())
                .middleTime(styleRegist.getMiddleTime().toString())
                .isActive(IsYesNo.YES)
                .insertData(nowDate)
                .updateData(nowDate)
                .descriptionNote(styleRegist.getDescriptionNote())
                .build()
        );
    }

    @Transactional
    public void styleUpdate(StyleUpdate styleUpdate) {
        SalonStyle salonStyle = salonStyleRepository.findSalonStyleByStyleGuid(styleUpdate.getStyleGuid());
        if (salonStyle == null) throw new RuntimeException("스타일이 존재하지 않습니다.");

        salonStyle.update(
                styleUpdate.getStyleName(),
                styleUpdate.getStyleDetail(),
                styleUpdate.getStyleDuration(),
                styleUpdate.getIsMiddleTime(),
                styleUpdate.getMiddleTime().toString(),
                LocalDateTime.now(),
                styleUpdate.getDescriptionNote()
        );
    }

    @Transactional
    public void styleActive(StyleActive styleActive) {
        SalonStyle salonStyle = salonStyleRepository.findSalonStyleByStyleGuid(styleActive.getStyleGuid());
        if (salonStyle == null) throw new RuntimeException("스타일이 존재하지 않습니다.");

        salonStyleRepository.updateSalonStyleByActiveStatus(styleActive.getStyleGuid(), styleActive.getIsActive(), LocalDateTime.now());
    }

    @Transactional
    public void styleDelete(String styleGuid) {
        SalonStyle salonStyle = salonStyleRepository.findSalonStyleByStyleGuid(UUID.fromString(styleGuid));
        if (salonStyle == null) throw new RuntimeException("스타일이 존재하지 않습니다.");

        salonStyleRepository.delete(salonStyle);
    }

    public Page<StyleList> styleListPage(String searchType, String searchValue, Long offset, int limit, String companyGuid) {
        Sort sort = Sort.by("seq").descending();
        Pageable pageable = PageRequest.of(offset.intValue(), limit, sort);

        return salonStyleRepositoryImpl.findStyleListPage(searchType, searchValue, offset, limit, pageable, UUID.fromString(companyGuid));
    }

    public StyleInfo getStyleInfo(String styleGuid) {
        StyleInfo styleInfo = salonStyleRepositoryImpl.findStyleInfo(UUID.fromString(styleGuid));

        styleInfo.setUserName(encryptStringSalt(styleInfo.getUserName()));
        styleInfo.setMiddleTime(gson.fromJson(styleInfo.getMiddleTimeString(), List.class));

        return styleInfo;
    }
}
