package com.project.salon.main.api.repository.report;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.report.QSalonAuditLog;
import com.project.salon.main.api.domain.report.SalonAuditLog;
import com.project.salon.main.api.dto.report.audit.AuditLogList;
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
public class SalonAuditLogRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonAuditLogRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonAuditLog.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonAuditLog qSalonAuditLog = QSalonAuditLog.salonAuditLog;
    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;
    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;

    public Page<AuditLogList> findAuditLogListPage(Long startTimestamp, Long endTimestamp, String searchType, String searchValue, Long offset, int limit, Pageable pageable) {
        OrderSpecifier<?> sortedColumn = qSalonAuditLog.seq.desc();

        Long setOffset = offset * limit;

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAuditLog.insertTimestamp.goe(startTimestamp));
        bb.and(qSalonAuditLog.insertTimestamp.loe(endTimestamp));

        List<AuditLogList> auditLogLists = jpaQueryFactory
                .select(Projections.fields(
                        AuditLogList.class,
                        qSalonAuditLog.auditGuid.as("auditGuid"),
                        qSalonAuditLog.controllerType.as("controllerType"),
                        qSalonAuditLog.controllerCategory.as("controllerCategory"),
                        qSalonAuditLog.adminGuid.as("userGuid"),
                        qSalonAuditLog.auditDetail.as("auditDetail"),
                        qSalonAuditLog.insertDate.as("insertDate"),
                        qSalonAdmin.adminID.as("userID"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonCompany.companyNumber.as("companyNumber"),
                        qSalonCompany.companyName.as("companyName")
                ))
                .from(qSalonAuditLog)
                .leftJoin(qSalonAdmin).on(qSalonAuditLog.adminGuid.eq(qSalonAdmin.adminGuid))
                .leftJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb, eqAdminName(searchType, searchValue), eqAdminID(searchType, searchValue), eqCompanyName(searchType, searchValue), eqActionType(searchType, searchValue), eqActionCategory(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qSalonAuditLog.count())
                .from(qSalonAuditLog)
                .leftJoin(qSalonAdmin).on(qSalonAuditLog.adminGuid.eq(qSalonAdmin.adminGuid))
                .where(bb, eqAdminName(searchType, searchValue), eqAdminID(searchType, searchValue), eqCompanyName(searchType, searchValue), eqActionType(searchType, searchValue), eqActionCategory(searchType, searchValue));

        return PageableExecutionUtils.getPage(auditLogLists, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqAdminName(String searchType, String searchValue) {
        if (!"userName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAdmin.adminName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqAdminID(String searchType, String searchValue) {
        if (!"userID".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAdmin.adminID.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqCompanyName(String searchType, String searchValue) {
        if (!"companyName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.companyName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqActionType(String searchType, String searchValue) {
        if (!"actionType".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAuditLog.controllerType.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqActionCategory(String searchType, String searchValue) {
        if (!"actionCategory".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAuditLog.controllerCategory.containsIgnoreCase(searchValue);
    }
}
