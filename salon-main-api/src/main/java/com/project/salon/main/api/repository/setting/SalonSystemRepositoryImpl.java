package com.project.salon.main.api.repository.setting;

import com.project.salon.main.api.domain.setting.QSalonSystem;
import com.project.salon.main.api.domain.setting.SalonSystem;
import com.project.salon.main.api.dto.setting.system.HoursInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SalonSystemRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalonSystemRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SalonSystem.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QSalonSystem qSalonSystem = QSalonSystem.salonSystem;

    public List<HoursInfo> findHoursInfoList(UUID companyGuid) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qSalonSystem.companyGuid.eq(companyGuid));

        return jpaQueryFactory
                .select(Projections.fields(
                        HoursInfo.class,
                        qSalonSystem.companyGuid.as("companyGuid"),
                        qSalonSystem.dayOfWeek.as("dayOfWeek"),
                        qSalonSystem.hoursCategory.as("hoursCategory"),
                        qSalonSystem.startTimeHour.as("startTimeHour"),
                        qSalonSystem.startTimeMinute.as("startTimeMinute"),
                        qSalonSystem.endTimeHour.as("endTimeHour"),
                        qSalonSystem.endTimeMinute.as("endTimeMinute")
                ))
                .from(qSalonSystem)
                .where(bb)
                .fetch();
    }
}
