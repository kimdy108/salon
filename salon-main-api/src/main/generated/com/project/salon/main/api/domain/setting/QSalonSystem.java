package com.project.salon.main.api.domain.setting;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonSystem is a Querydsl query type for SalonSystem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonSystem extends EntityPathBase<SalonSystem> {

    private static final long serialVersionUID = -1511365606L;

    public static final QSalonSystem salonSystem = new QSalonSystem("salonSystem");

    public final com.project.salon.main.api.domain.common.QSalonCommonBase _super = new com.project.salon.main.api.domain.common.QSalonCommonBase(this);

    public final ComparablePath<java.util.UUID> companyGuid = createComparable("companyGuid", java.util.UUID.class);

    public final NumberPath<Long> companySeq = createNumber("companySeq", Long.class);

    public final EnumPath<java.time.DayOfWeek> dayOfWeek = createEnum("dayOfWeek", java.time.DayOfWeek.class);

    //inherited
    public final StringPath descriptionNote = _super.descriptionNote;

    public final StringPath endTimeHour = createString("endTimeHour");

    public final StringPath endTimeMinute = createString("endTimeMinute");

    public final EnumPath<com.project.salon.main.api.dto.constant.system.HoursCategory> hoursCategory = createEnum("hoursCategory", com.project.salon.main.api.dto.constant.system.HoursCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = _super.isActive;

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    public final StringPath startTimeHour = createString("startTimeHour");

    public final StringPath startTimeMinute = createString("startTimeMinute");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QSalonSystem(String variable) {
        super(SalonSystem.class, forVariable(variable));
    }

    public QSalonSystem(Path<? extends SalonSystem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonSystem(PathMetadata metadata) {
        super(SalonSystem.class, metadata);
    }

}

