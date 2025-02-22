package com.project.salon.main.api.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonCommonBase is a Querydsl query type for SalonCommonBase
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QSalonCommonBase extends EntityPathBase<SalonCommonBase> {

    private static final long serialVersionUID = 1838173130L;

    public static final QSalonCommonBase salonCommonBase = new QSalonCommonBase("salonCommonBase");

    public final StringPath descriptionNote = createString("descriptionNote");

    public final DateTimePath<java.time.LocalDateTime> insertDate = createDateTime("insertDate", java.time.LocalDateTime.class);

    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = createEnum("isActive", com.project.salon.main.api.dto.constant.common.IsYesNo.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QSalonCommonBase(String variable) {
        super(SalonCommonBase.class, forVariable(variable));
    }

    public QSalonCommonBase(Path<? extends SalonCommonBase> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonCommonBase(PathMetadata metadata) {
        super(SalonCommonBase.class, metadata);
    }

}

