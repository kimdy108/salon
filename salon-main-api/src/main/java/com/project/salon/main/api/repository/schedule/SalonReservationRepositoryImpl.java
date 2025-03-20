package com.project.salon.main.api.repository.schedule;

import com.project.salon.main.api.domain.admin.QSalonAdmin;
import com.project.salon.main.api.domain.manage.QSalonCompany;
import com.project.salon.main.api.domain.schedule.QSalonReservation;
import com.project.salon.main.api.domain.schedule.SalonReservation;
import com.project.salon.main.api.dto.schedule.reservation.ReservationDayList;
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
    QSalonReservation qSalonReservation = QSalonReservation.salonReservation;

    public List<ReservationMonthList> findReservationByMonth (String reservationYear, String reservationMonth, UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonReservation.reservationYear.eq(reservationYear));
        bb.and(qSalonReservation.reservationMonth.eq(reservationMonth));
        if (!EMPTY_UUID.equals(companyGuid)) bb.and(qSalonAdmin.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        ReservationMonthList.class,
                        qSalonAdmin.adminName.as("userName"),
                        qSalonReservation.seq.count().as("reservationCount"),
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

    public List<ReservationDayList> findReservationByDay (String startReservationYear, String startReservationMonth, String startReservationDay, String endReservationYear, String endReservationMonth, String endReservationDay, UUID companyGuid) {
        return null;
    }
}
