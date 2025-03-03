package com.project.salon.main.api.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonCommonLog is a Querydsl query type for SalonCommonLog
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QSalonCommonLog extends EntityPathBase<SalonCommonLog> {

    private static final long serialVersionUID = -1187620053L;

    public static final QSalonCommonLog salonCommonLog = new QSalonCommonLog("salonCommonLog");

    public final DateTimePath<java.time.LocalDateTime> insertDate = createDateTime("insertDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> insertTimestamp = createNumber("insertTimestamp", Long.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QSalonCommonLog(String variable) {
        super(SalonCommonLog.class, forVariable(variable));
    }

    public QSalonCommonLog(Path<? extends SalonCommonLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonCommonLog(PathMetadata metadata) {
        super(SalonCommonLog.class, metadata);
    }

}

