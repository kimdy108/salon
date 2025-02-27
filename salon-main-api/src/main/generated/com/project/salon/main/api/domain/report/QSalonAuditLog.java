package com.project.salon.main.api.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonAuditLog is a Querydsl query type for SalonAuditLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonAuditLog extends EntityPathBase<SalonAuditLog> {

    private static final long serialVersionUID = 2075741408L;

    public static final QSalonAuditLog salonAuditLog = new QSalonAuditLog("salonAuditLog");

    public final com.project.salon.main.api.domain.common.QSalonCommonLog _super = new com.project.salon.main.api.domain.common.QSalonCommonLog(this);

    public final StringPath auditDetail = createString("auditDetail");

    public final StringPath controllerCategory = createString("controllerCategory");

    public final StringPath controllerType = createString("controllerType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    public final ComparablePath<java.util.UUID> userGuid = createComparable("userGuid", java.util.UUID.class);

    public QSalonAuditLog(String variable) {
        super(SalonAuditLog.class, forVariable(variable));
    }

    public QSalonAuditLog(Path<? extends SalonAuditLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonAuditLog(PathMetadata metadata) {
        super(SalonAuditLog.class, metadata);
    }

}

