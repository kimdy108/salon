package com.project.salon.main.api.domain.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonLoginLog is a Querydsl query type for SalonLoginLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonLoginLog extends EntityPathBase<SalonLoginLog> {

    private static final long serialVersionUID = -1172527470L;

    public static final QSalonLoginLog salonLoginLog = new QSalonLoginLog("salonLoginLog");

    public final com.project.salon.main.api.domain.common.QSalonCommonLog _super = new com.project.salon.main.api.domain.common.QSalonCommonLog(this);

    public final ComparablePath<java.util.UUID> adminGuid = createComparable("adminGuid", java.util.UUID.class);

    public final NumberPath<Long> adminSeq = createNumber("adminSeq", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final NumberPath<Long> insertTimestamp = _super.insertTimestamp;

    public final StringPath loginID = createString("loginID");

    public final StringPath loginPassword = createString("loginPassword");

    public final StringPath loginResult = createString("loginResult");

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    public QSalonLoginLog(String variable) {
        super(SalonLoginLog.class, forVariable(variable));
    }

    public QSalonLoginLog(Path<? extends SalonLoginLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonLoginLog(PathMetadata metadata) {
        super(SalonLoginLog.class, metadata);
    }

}

