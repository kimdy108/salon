package com.project.salon.main.api.repository.admin;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.admin.SalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.dto.admin.AdminInfo;
import com.project.salon.main.api.dto.constant.admin.AdminRole;
import com.project.salon.main.api.dto.constant.common.IsYesNo;
import com.project.salon.main.api.dto.dashboard.user.UserByMonth;
import com.project.salon.main.api.dto.manage.master.MasterInfo;
import com.project.salon.main.api.dto.manage.master.MasterList;
import com.project.salon.main.api.dto.manage.user.UserInfo;
import com.project.salon.main.api.dto.manage.user.UserList;
import com.project.salon.main.api.dto.manage.user.UserListAll;
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
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
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
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb)
                .fetchOne();
    }

    public List<UserListAll> findUserListAll(UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonCompany.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        UserListAll.class,
                        qSalonAdmin.adminGuid.as("userGuid"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonCompany.companyName.as("companyName")
                ))
                .from(qSalonAdmin)
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb)
                .fetch();
    }

    public Page<MasterList> findMasterListPage(String searchType, String searchValue, Long offset, int limit, Pageable pageable) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminType.eq("NORMAL"));
        bb.and(qSalonAdmin.adminRole.eq(AdminRole.MASTER));

        OrderSpecifier<?> sortedColumn = qSalonAdmin.seq.desc();

        Long setOffset = offset * limit;

        List<MasterList> masterLists = jpaQueryFactory
                .select(Projections.fields(
                        MasterList.class,
                        qSalonAdmin.adminGuid.as("masterGuid"),
                        qSalonAdmin.adminID.as("masterID"),
                        qSalonAdmin.adminName.as("masterName"),
                        qSalonAdmin.adminRole.as("masterRole"),
                        qSalonAdmin.isActive.as("isActive"),
                        qSalonAdmin.insertDate.as("insertDate"),
                        qSalonAdmin.updateDate.as("updateDate")
                ))
                .from(qSalonAdmin)
                .where(eqCompanyName(searchType, searchValue), eqUserName(searchType, searchValue), bb)
                .orderBy(sortedColumn)
                .limit(limit)
                .offset(setOffset)
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(qSalonAdmin.count())
                .from(qSalonAdmin)
                .where(eqCompanyName(searchType, searchValue), eqUserName(searchType, searchValue), bb);

        return PageableExecutionUtils.getPage(masterLists, pageable, countQuery::fetchOne);
    }

    public MasterInfo findMasterInfo(UUID masterGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminGuid.eq(masterGuid));

        return  jpaQueryFactory
                .select(Projections.fields(
                        MasterInfo.class,
                        qSalonAdmin.adminGuid.as("masterGuid"),
                        qSalonAdmin.adminID.as("masterID"),
                        qSalonAdmin.adminName.as("masterName"),
                        qSalonAdmin.adminRole.as("masterRole"),
                        qSalonAdmin.adminPhone.as("masterPhone"),
                        qSalonAdmin.adminEmail.as("masterEmail"),
                        qSalonAdmin.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonAdmin)
                .where(bb)
                .fetchOne();
    }

    public AdminInfo findAdminInfo(UUID adminGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminGuid.eq(adminGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        AdminInfo.class,
                        qSalonAdmin.adminGuid.as("adminGuid"),
                        qSalonAdmin.adminRole.as("adminRole"),
                        qSalonAdmin.adminID.as("adminID"),
                        qSalonAdmin.adminName.as("adminName"),
                        qSalonAdmin.adminPhone.as("adminPhone"),
                        qSalonAdmin.adminEmail.as("adminEmail"),
                        qSalonAdmin.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonAdmin)
                .where(bb)
                .fetchOne();
    }

    public Long findUserCurrent(int year, int month, boolean isAll) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminRole.ne(AdminRole.MASTER));
        if (year != 0) bb.and(qSalonAdmin.insertDate.year().eq(year));
        if (month != 0) bb.and(qSalonAdmin.insertDate.month().eq(month));
        if (!isAll) bb.and(qSalonAdmin.isActive.eq(IsYesNo.YES));

        return jpaQueryFactory
                .select(qSalonAdmin.seq.count())
                .from(qSalonAdmin)
                .where(bb)
                .fetchOne();
    }

    public List<UserByMonth> findUserByMonth(int year) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonAdmin.adminRole.ne(AdminRole.MASTER));
        bb.and(qSalonAdmin.insertDate.year().eq(year));

        return jpaQueryFactory
                        .select(Projections.fields(
                                UserByMonth.class,
                                qSalonAdmin.insertDate.month().as("month"),
                                Expressions.numberTemplate(Integer.class, "{0}", qSalonAdmin.count()).as("count")
                        ))
                        .from(qSalonAdmin)
                        .where(bb)
                        .groupBy(qSalonAdmin.insertDate.month())
                        .fetch();
    }

    private BooleanExpression eqCompanyName(String searchType, String searchValue) {
        if(!"companyName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonCompany.companyName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqUserName(String searchType, String searchValue) {
        if(!"userName".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAdmin.adminName.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqUserID(String searchType, String searchValue) {
        if(!"userID".equals(searchType) || "".equals(searchValue)) return null;
        return qSalonAdmin.adminID.containsIgnoreCase(searchValue);
    }
}
