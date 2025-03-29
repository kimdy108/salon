package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.schedule.QSalonEmployment;
import com.project.salon.main.api.domain.schedule.SalonEmployment;
import com.project.salon.main.api.dto.schedule.employment.EmploymentInfo;
import com.project.salon.main.api.dto.schedule.employment.EmploymentList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.*;

@Repository
public class SalonEmploymentRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonEmploymentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonEmployment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;
    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;
    QSalonEmployment qSalonEmployment = QSalonEmployment.salonEmployment;
    
    public List<EmploymentList> findEmploymentListByYearAndMonth(String employmentYear, String employmentMonth, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonEmployment.employmentYear.eq(employmentYear));
        bb.and(qSalonEmployment.employmentMonth.eq(employmentMonth));
        if (!companyGuid.equals(EMPTY_UUID)) bb.and(qSalonCompany.companyGuid.eq(companyGuid));
        
        return jpaQueryFactory
                .select(Projections.fields(
                        EmploymentList.class,
                        qSalonEmployment.employmentGuid.as("employmentGuid"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonEmployment.employmentYear.as("employmentYear"),
                        qSalonEmployment.employmentMonth.as("employmentMonth"),
                        qSalonEmployment.employmentDay.as("employmentDay"),
                        qSalonEmployment.employmentCategory.as("employmentCategory")
                ))
                .from(qSalonEmployment)
                .innerJoin(qSalonAdmin).on(qSalonEmployment.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb)
                .fetch();
    }

    public EmploymentInfo findEmploymentInfo (UUID employmentGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonEmployment.employmentGuid.eq(employmentGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        EmploymentInfo.class,
                        qSalonEmployment.employmentGuid.as("employmentGuid"),
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonEmployment.employmentCategory.as("employmentCategory"),
                        qSalonEmployment.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonEmployment)
                .innerJoin(qSalonAdmin).on(qSalonEmployment.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .where(bb)
                .fetchOne();
    }

    public Long findEmploymentCurrent(String year, String month, String day, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        if (!"0".equals(year)) bb.and(qSalonEmployment.employmentYear.eq(year));
        if (!"0".equals(month)) bb.and(qSalonEmployment.employmentMonth.eq(month));
        if (!"0".equals(day)) bb.and(qSalonEmployment.employmentDay.eq(day));
        if (!EMPTY_UUID.equals(companyGuid)) bb.and(qSalonAdmin.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(qSalonEmployment.seq.count())
                .from(qSalonEmployment)
                .innerJoin(qSalonAdmin).on(qSalonEmployment.adminSeq.eq(qSalonAdmin.seq))
                .where(bb)
                .fetchOne();
    }
}
