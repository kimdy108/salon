package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.schedule.QSalonReservation;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.domain.setting.QSalonStyle;
import com.project.salon.main.api.dto.schedule.reservation.ReservationDayList;
import com.project.salon.main.api.dto.schedule.reservation.ReservationInfo;
import com.project.salon.main.api.dto.schedule.reservation.ReservationMonthList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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

    QSalonAdmin qSalonAdmin = QSalonAdmin.salonAdmin;
    QSalonStyle qSalonStyle = QSalonStyle.salonStyle;
    QSalonReservation qSalonReservation = QSalonReservation.salonReservation;

    public ReservationInfo findReservationInfo (UUID reservationGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonReservation.reservationGuid.eq(reservationGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        ReservationInfo.class,
                        qSalonReservation.reservationGuid.as("reservationGuid"),
                        qSalonAdmin.adminName.as("userName"),
                        qSalonStyle.styleName.as("styleName"),
                        qSalonReservation.clientName.as("clientName"),
                        qSalonReservation.clientNumber.as("clientNumber")
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
}
