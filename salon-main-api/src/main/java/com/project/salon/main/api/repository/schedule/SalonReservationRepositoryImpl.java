package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.schedule.QSalonEmployment;
import com.project.salon.main.api.domain.schedule.QSalonReservation;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.domain.setting.QSalonStyle;
import com.project.salon.main.api.dto.dashboard.schedule.ScheduleByMonth;
import com.project.salon.main.api.dto.dashboard.schedule.ScheduleByUser;
import com.project.salon.main.api.dto.schedule.reservation.ReservationDayList;
import com.project.salon.main.api.dto.schedule.reservation.ReservationInfo;
import com.project.salon.main.api.dto.schedule.reservation.ReservationMonthList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.project.salon.main.api.utils.Common.EMPTY_UUID;

@Repository
public class SalonReservationRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonReservationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonReservation.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonCompany qSalonCompany = QSalonCompany.salonCompany;
    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;
    QSalonStyle qSalonStyle = QSalonStyle.salonStyle;
    QSalonReservation qSalonReservation = QSalonReservation.salonReservation;
    QSalonEmployment qSalonEmployment = QSalonEmployment.salonEmployment;

    public ReservationInfo findReservationInfo (UUID reservationGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonReservation.reservationGuid.eq(reservationGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        ReservationInfo.class,
                        qSalonReservation.adminGuid.as("userGuid"),
                        qSalonReservation.reservationGuid.as("reservationGuid"),
                        qSalonReservation.reservationYear.as("reservationYear"),
                        qSalonReservation.reservationMonth.as("reservationMonth"),
                        qSalonReservation.reservationDay.as("reservationDay"),
                        qSalonReservation.reservationHour.as("reservationHour"),
                        qSalonReservation.reservationMinute.as("reservationMinute"),
                        qSalonStyle.styleName.as("styleName"),
                        qSalonStyle.styleDuration.as("styleDuration"),
                        qSalonReservation.clientName.as("clientName"),
                        qSalonReservation.clientNumber.as("clientNumber"),
                        qSalonReservation.descriptionNote.as("descriptionNote")
                ))
                .from(qSalonReservation)
                .innerJoin(qSalonAdmin).on(qSalonReservation.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonStyle).on(qSalonReservation.styleSeq.eq(qSalonStyle.seq))
                .where(bb)
                .fetchOne();
    }

    public List<ReservationMonthList> findReservationByMonth (String reservationYear, String reservationMonth, UUID companyGuid, UUID userGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonReservation.reservationYear.eq(reservationYear));
        bb.and(qSalonReservation.reservationMonth.eq(reservationMonth));
        bb.and(qSalonAdmin.companyGuid.eq(companyGuid));
        if (!EMPTY_UUID.equals(userGuid)) bb.and(qSalonAdmin.adminGuid.eq(userGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        ReservationMonthList.class,
                        qSalonAdmin.adminName.as("userName"),
                        qSalonReservation.reservationPartnerGuid.countDistinct().as("reservationCount"),
                        qSalonReservation.reservationYear.as("reservationYear"),
                        qSalonReservation.reservationMonth.as("reservationMonth"),
                        qSalonReservation.reservationDay.as("reservationDay")
                ))
                .from(qSalonReservation)
                .innerJoin(qSalonAdmin).on(qSalonReservation.adminSeq.eq(qSalonAdmin.seq))
                .where(bb)
                .groupBy(qSalonAdmin.seq, qSalonReservation.reservationYear, qSalonReservation.reservationMonth, qSalonReservation.reservationDay)
                .fetch();
    }

    public List<ReservationDayList> findReservationByDay (String startReservationYear, String startReservationMonth, String startReservationDay, String endReservationYear, String endReservationMonth, String endReservationDay, UUID companyGuid, UUID adminGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonReservation.reservationYear.goe(startReservationYear));
        bb.and(qSalonReservation.reservationMonth.goe(startReservationMonth));
        bb.and(qSalonReservation.reservationDay.goe(startReservationDay));
        bb.and(qSalonReservation.reservationYear.loe(endReservationYear));
        bb.and(qSalonReservation.reservationMonth.loe(endReservationMonth));
        bb.and(qSalonReservation.reservationDay.loe(endReservationDay));
        bb.and(qSalonAdmin.companyGuid.eq(companyGuid));
        if (!EMPTY_UUID.equals(adminGuid)) bb.and(qSalonAdmin.adminGuid.eq(adminGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        ReservationDayList.class,
                        qSalonReservation.reservationGuid.as("reservationGuid"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonStyle.styleName.as("styleName"),
                        qSalonReservation.clientName.as("clientName"),
                        qSalonReservation.reservationYear.as("reservationYear"),
                        qSalonReservation.reservationMonth.as("reservationMonth"),
                        qSalonReservation.reservationDay.as("reservationDay"),
                        qSalonReservation.reservationHour.as("reservationHour"),
                        qSalonReservation.reservationMinute.as("reservationMinute"),
                        qSalonReservation.reservationEndHour.as("reservationEndHour"),
                        qSalonReservation.reservationEndMinute.as("reservationEndMinute")
                ))
                .from(qSalonReservation)
                .innerJoin(qSalonAdmin).on(qSalonReservation.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonStyle).on(qSalonReservation.styleSeq.eq(qSalonStyle.seq))
                .where(bb)
                .fetch();
    }

    public Long findScheduleCurrent(String year, String month, String day, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        if (!"0".equals(year)) bb.and(qSalonReservation.reservationYear.eq(year));
        if (!"0".equals(month)) bb.and(qSalonReservation.reservationMonth.eq(month));
        if (!"0".equals(day)) bb.and(qSalonReservation.reservationDay.eq(day));
        if (!EMPTY_UUID.equals(companyGuid)) bb.and(qSalonAdmin.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(qSalonReservation.seq.count())
                .from(qSalonReservation)
                .innerJoin(qSalonAdmin).on(qSalonReservation.adminSeq.eq(qSalonAdmin.seq))
                .where(bb)
                .fetchOne();
    }

    public List<ScheduleByMonth> findScheduleByMonth (String year, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonReservation.reservationYear.eq(year));
        if (!EMPTY_UUID.equals(companyGuid)) bb.and(qSalonAdmin.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        ScheduleByMonth.class,
                        qSalonReservation.reservationMonth.castToNum(Integer.class).as("month"),
                        Expressions.numberTemplate(Integer.class, "{0}", qSalonReservation.count()).as("count")
                ))
                .from(qSalonReservation)
                .innerJoin(qSalonAdmin).on(qSalonReservation.adminSeq.eq(qSalonAdmin.seq))
                .where(bb)
                .groupBy(qSalonReservation.reservationMonth)
                .fetch();
    }

    public List<ScheduleByUser> findScheduleByUser (String year, String month, String day, UUID companyGuid) {
        BooleanBuilder bbMain = new BooleanBuilder();
        bbMain.and(qSalonEmployment.employmentYear.eq(year));
        bbMain.and(qSalonEmployment.employmentMonth.eq(month));
        bbMain.and(qSalonEmployment.employmentDay.eq(day));
        if (!EMPTY_UUID.equals(companyGuid)) bbMain.and(qSalonCompany.companyGuid.eq(companyGuid));

        BooleanBuilder bbSub = new BooleanBuilder();
        bbSub.and(qSalonReservation.adminSeq.eq(qSalonAdmin.seq));
        bbSub.and(qSalonReservation.reservationYear.eq(year));
        bbSub.and(qSalonReservation.reservationMonth.eq(month));
        bbSub.and(qSalonReservation.reservationDay.eq(day));

        return jpaQueryFactory
                .select(Projections.fields(
                        ScheduleByUser.class,
                        qSalonCompany.companyName.as("companyName"),
                        qSalonAdmin.adminName.as("userName"),
                        ExpressionUtils.as(JPAExpressions
                                .select(qSalonReservation.reservationPartnerGuid.countDistinct())
                                .from(qSalonReservation)
                                .where(bbSub), "scheduleCount")
                ))
                .from(qSalonReservation)
                .innerJoin(qSalonAdmin).on(qSalonReservation.adminSeq.eq(qSalonAdmin.seq))
                .innerJoin(qSalonCompany).on(qSalonAdmin.companySeq.eq(qSalonCompany.seq))
                .innerJoin(qSalonEmployment).on(qSalonAdmin.seq.eq(qSalonEmployment.adminSeq))
                .where(bbMain)
                .groupBy(qSalonReservation.adminSeq)
                .fetch();
    }
}
