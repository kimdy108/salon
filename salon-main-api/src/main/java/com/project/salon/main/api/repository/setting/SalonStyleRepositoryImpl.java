package com.project.salon.main.api.repository.setting;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.setting.QSalonStyle;
import com.project.salon.main.api.domain.setting.SalonStyle;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.setting.style.StyleInfo;
import com.project.salon.main.api.dto.setting.style.StyleList;
import com.project.salon.main.api.dto.setting.style.StyleListAll;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.EMPTY_UUID;

@Repository
public class SalonStyleRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonStyleRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonStyle.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;
    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;
    QSalonStyle qSalonStyle = QSalonStyle.salonStyle;

    public Page<StyleList> findStyleListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        if (!companyGuid.equals(EMPTY_UUID)) bb.and(qSalonCompany.companyGuid.eq(companyGuid));

        OrderSpecifier<?> sortedColumn = qSalonStyle.seq.desc();

        Long setOffset = offset * limit;

        List<StyleList> styleLists = jpaQueryFactory
                .select(Projections.fields(
                        StyleList.class,
                        qSalonStyle.styleGuid.as("styleGuid"),
                        qSalonStyle.styleName.as("styleName"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonStyle.styleDuration.as("styleDuration"),
                        qSalonStyle.isActive.as("isActive"),
                        qSalonStyle.isMiddleTime.as("isMiddleTime"),
                        qSalonStyle.insertDate.as("insertDate"),
                        qSalonStyle.updateDate.as("updateDate")
                ))
                .from(qSalonStyle)
                .innerJoin(qSalonAdmin).on(qSalonStyle.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb, eqCompanyName(searchType, searchValue), eqUserName(searchType, searchValue), eqStyleName(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qSalonStyle.count())
                .from(qSalonStyle)
                .where(bb, eqCompanyName(searchType, searchValue), eqUserName(searchType, searchValue), eqStyleName(searchType, searchValue));

        return PageableExecutionUtils.getPage(styleLists, pageable, countQuery::fetchOne);
    }

    public StyleInfo findStyleInfo(UUID styleGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonStyle.styleGuid.eq(styleGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        StyleInfo.class,
                        qSalonStyle.styleGuid.as("styleGuid"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonStyle.styleName.as("styleName"),
                        qSalonStyle.styleDetail.as("styleDetail"),
                        qSalonStyle.styleDuration.as("styleDuration"),
                        qSalonStyle.isMiddleTime.as("isMiddleTime"),
                        qSalonStyle.middleTime.as("middleTimeString"),
                        qSalonStyle.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonStyle)
                .innerJoin(qSalonAdmin).on(qSalonStyle.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb)
                .fetchOne();
    }

    public List<StyleListAll> findStyleListAll(UUID userGuid, boolean isAll) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminGuid.eq(userGuid));
        if (!isAll) bb.and(qSalonStyle.isActive.eq(IsYesNo.YES));

        return jpaQueryFactory
                .select(Projections.fields(
                        StyleListAll.class,
                        qSalonStyle.styleGuid.as("styleGuid"),
                        qSalonStyle.styleName.as("styleName"),
                        qSalonStyle.styleDuration.as("styleDuration")
                ))
                .from(qSalonStyle)
                .innerJoin(qSalonAdmin).on(qSalonStyle.adminSeq.eq(qSalonAdmin.seq))
                .where(bb)
                .fetch();
    }

    private BooleanExpression eqCompanyName(String searchType, String searchValue) {
        if(!"companyName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.companyName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqUserName(String searchType, String searchValue) {
        if(!"userName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.managerName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqStyleName(String searchType, String searchValue) {
        if(!"styleName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.managerName.containsIgnoreCase(searchValue);
    }
}
