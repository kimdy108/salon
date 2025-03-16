package com.project.salon.main.api.domain.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonReservation is a Querydsl query type for SalonReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonReservation extends EntityPathBase<SalonReservation> {

    private static final long serialVersionUID = -723882254L;

    public static final QSalonReservation salonReservation = new QSalonReservation("salonReservation");

    public final com.project.salon.main.api.domain.common.QSalonCommonBase _super = new com.project.salon.main.api.domain.common.QSalonCommonBase(this);

    public final ComparablePath<java.util.UUID> adminGuid = createComparable("adminGuid", java.util.UUID.class);

    public final NumberPath<Long> adminSeq = createNumber("adminSeq", Long.class);

    public final StringPath clientName = createString("clientName");

    public final StringPath clientNumber = createString("clientNumber");

    //inherited
    public final StringPath descriptionNote = _super.descriptionNote;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = _super.isActive;

    public final StringPath reservationDay = createString("reservationDay");

    public final StringPath reservationEndHour = createString("reservationEndHour");

    public final StringPath reservationEndMinute = createString("reservationEndMinute");

    public final ComparablePath<java.util.UUID> reservationGuid = createComparable("reservationGuid", java.util.UUID.class);

    public final StringPath reservationHour = createString("reservationHour");

    public final StringPath reservationMinute = createString("reservationMinute");

    public final StringPath reservationMonth = createString("reservationMonth");

    public final ComparablePath<java.util.UUID> reservationPartnerGuid = createComparable("reservationPartnerGuid", java.util.UUID.class);

    public final StringPath reservationYear = createString("reservationYear");

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    public final ComparablePath<java.util.UUID> styleGuid = createComparable("styleGuid", java.util.UUID.class);

    public final NumberPath<Long> styleSeq = createNumber("styleSeq", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QSalonReservation(String variable) {
        super(SalonReservation.class, forVariable(variable));
    }

    public QSalonReservation(Path<? extends SalonReservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonReservation(PathMetadata metadata) {
        super(SalonReservation.class, metadata);
    }

}

