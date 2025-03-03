package com.project.salon.main.api.repository.report;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.report.QSalonLoginLog;
import com.project.salon.main.api.domain.report.SalonLoginLog;
import com.project.salon.main.api.dto.report.login.LoginLogList;
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
public class SalonLoginLogRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonLoginLogRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonLoginLog.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonLoginLog qSalonLoginLog = QSalonLoginLog.salonLoginLog;
    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;
    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;

    public Page<LoginLogList> findLoginListPage(Long startTimestamp, Long endTimestamp, String searchType, String searchValue, Long offset, int limit, Pageable pageable, UUID companyGuid) {
        OrderSpecifier<?> sortedColumn = qSalonLoginLog.seq.desc();

        Long setOffset = offset * limit;

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonLoginLog.insertTimestamp.goe(startTimestamp));
        bb.and(qSalonLoginLog.insertTimestamp.loe(endTimestamp));
        if (!companyGuid.equals(EMPTY_UUID)) bb.and(qSalonAdmin.companyGuid.eq(companyGuid));

        List<LoginLogList> loginLogLists = jpaQueryFactory
                .select(Projections.fields(
                        LoginLogList.class,
                        qSalonLoginLog.loginResult.as("loginResult"),
                        qSalonLoginLog.loginID.as("loginID"),
                        qSalonLoginLog.adminGuid.as("userGuid"),
                        qSalonLoginLog.insertDate.as("insertDate"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonCompany.companyNumber.as("companyNumber"),
                        qSalonCompany.companyName.as("companyName")
                ))
                .from(qSalonLoginLog)
                .leftJoin(qSalonAdmin).on(qSalonLoginLog.adminSeq.eq(qSalonAdmin.seq))
                .leftJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb, eqAdminID(searchType, searchValue), eqAdminName(searchType, searchValue), eqCompanyName(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qSalonLoginLog.count())
                .from(qSalonLoginLog)
                .leftJoin(qSalonAdmin).on(qSalonLoginLog.adminSeq.eq(qSalonAdmin.seq))
                .leftJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb, eqAdminID(searchType, searchValue), eqAdminName(searchType, searchValue), eqCompanyName(searchType, searchValue));

        return PageableExecutionUtils.getPage(loginLogLists, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqAdminName(String searchType, String searchValue) {
        if (!"adminName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAdmin.adminName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqAdminID(String searchType, String searchValue) {
        if (!"actionType".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonLoginLog.loginID.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqCompanyName(String searchType, String searchValue) {
        if (!"companyName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.companyName.containsIgnoreCase(searchValue);
    }
}
