package com.project.salon.main.api.repository.admin;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.dto.manage.user.UserInfo;
import com.project.salon.main.api.dto.manage.user.UserList;
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
public class SalonAdminRepositoyImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonAdminRepositoyImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonAdmin.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;
    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;

    public Page<UserList> findUserListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminType.eq("NORMAL"));
        bb.and(qSalonAdmin.companyGuid.ne(EMPTY_UUID));
        if (!companyGuid.equals(EMPTY_UUID)) bb.and(qSalonCompany.companyGuid.eq(companyGuid));

        OrderSpecifier<?> sortedColumn = qSalonAdmin.seq.desc();

        Long setOffset = offset * limit;

        List<UserList> userLists = jpaQueryFactory
                .select(Projections.fields(
                        UserList.class,
                        qSalonAdmin.adminGuid.as("userGuid"),
                        qSalonAdmin.adminID.as("userID"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminRole.as("userRole"),
                        qSalonAdmin.isActive.as("isActive"),
                        qSalonAdmin.insertDate.as("insertDate"),
                        qSalonAdmin.updateDate.as("updateDate")
                ))
                .from(qSalonAdmin)
                .leftJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(eqCompanyName(searchType, searchValue), eqUserName(searchType, searchValue), bb)
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qSalonAdmin.count())
                .from(qSalonAdmin)
                .where(eqCompanyName(searchType, searchValue), eqUserName(searchType, searchValue), bb);

        return PageableExecutionUtils.getPage(userLists, pageable, countQuery::fetchOne);
    }

    public UserInfo findUserInfo(UUID userGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminGuid.eq(userGuid));

        return  jpaQueryFactory
                .select(Projections.fields(
                        UserInfo.class,
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminGuid.as("userGuid"),
                        qSalonAdmin.adminID.as("userID"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonAdmin.adminRole.as("userRole"),
                        qSalonAdmin.adminPhone.as("userPhone"),
                        qSalonAdmin.adminEmail.as("userEmail"),
                        qSalonAdmin.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonAdmin)
                .leftJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb)
                .fetchOne();
    }

    private BooleanExpression eqCompanyName(String searchType, String searchValue) {
        if(!"companyName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.companyName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqUserName(String searchType, String searchValue) {
        if(!"userName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.managerName.containsIgnoreCase(searchValue);
    }
}
