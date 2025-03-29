package com.project.salon.main.api.repository.manage;

import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.manage.SalonCompany;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.dashboard.company.CompanyByMonth;
import com.project.salon.main.api.dto.manage.company.CompanyInfo;
import com.project.salon.main.api.dto.manage.company.CompanyList;
import com.project.salon.main.api.dto.manage.company.CompanyListAll;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SalonCompanyRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonCompanyRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonCompany.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;

    public Page<CompanyList> findCompanyListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable) {
        OrderSpecifier<?> sortedColumn = qSalonCompany.seq.desc();

        Long setOffset = offset * limit;

        List<CompanyList> companyLists = jpaQueryFactory
                .select(Projections.fields(
                        CompanyList.class,
                        qSalonCompany.companyGuid.as("companyGuid"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonCompany.managerName.as("managerName"),
                        qSalonCompany.managerPhone.as("managerPhone"),
                        qSalonCompany.isActive.as("isActive"),
                        qSalonCompany.insertDate.as("insertDate"),
                        qSalonCompany.updateDate.as("updateDate")
                ))
                .from(qSalonCompany)
                .where(eqCompanyName(searchType, searchValue), eqManagerName(searchType, searchValue))
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qSalonCompany.count())
                .from(qSalonCompany)
                .where(eqCompanyName(searchType, searchValue), eqManagerName(searchType, searchValue));

        return PageableExecutionUtils.getPage(companyLists, pageable, countQuery::fetchOne);
    }

    public CompanyInfo findCompanyInfo(UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonCompany.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        CompanyInfo.class,
                        qSalonCompany.companyGuid.as("companyGuid"),
                        qSalonCompany.companyNumber.as("companyNumber"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonCompany.companyAddress.as("companyAddress"),
                        qSalonCompany.managerName.as("managerName"),
                        qSalonCompany.managerPhone.as("managerPhone"),
                        qSalonCompany.insertDate.as("insertDate"),
                        qSalonCompany.updateDate.as("updateDate"),
                        qSalonCompany.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonCompany)
                .where(bb)
                .fetchOne();
    }

    public List<CompanyListAll> findCompanyListAll() {
        return jpaQueryFactory
                .select(Projections.fields(
                        CompanyListAll.class,
                        qSalonCompany.companyGuid.as("companyGuid"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonCompany.managerName.as("managerName")
                ))
                .from(qSalonCompany)
                .fetch();
    }

    public Long findCompanyCurrent(int year, int month, boolean isAll) {
        BooleanBuilder bb = new BooleanBuilder();
        if (year != 0) bb.and(qSalonCompany.insertDate.year().eq(year));
        if (month != 0) bb.and(qSalonCompany.insertDate.month().eq(month));
        if (!isAll) bb.and(qSalonCompany.isActive.eq(IsYesNo.YES));

        return jpaQueryFactory
                .select(qSalonCompany.seq.count())
                .from(qSalonCompany)
                .where(bb)
                .fetchOne();
    }

    public List<CompanyByMonth> findCompanyByMonth(int year) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonCompany.insertDate.year().eq(year));

        return jpaQueryFactory
                .select(Projections.fields(
                        CompanyByMonth.class,
                        qSalonCompany.insertDate.month().as("month"),
                        Expressions.numberTemplate(Integer.class, "{0}", qSalonCompany.count()).as("count")
                ))
                .from(qSalonCompany)
                .where(bb)
                .groupBy(qSalonCompany.insertDate.month())
                .fetch();
    }

    private BooleanExpression eqCompanyName(String searchType, String searchValue) {
        if(!"companyName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.companyName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqManagerName(String searchType, String searchValue) {
        if(!"managerName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.managerName.containsIgnoreCase(searchValue);
    }
}
