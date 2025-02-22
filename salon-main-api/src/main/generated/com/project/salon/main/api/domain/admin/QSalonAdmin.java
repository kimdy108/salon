package com.project.salon.main.api.domain.admin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonAdmin is a Querydsl query type for SalonAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonAdmin extends EntityPathBase<SalonAdmin> {

    private static final long serialVersionUID = 2117597861L;

    public static final QSalonAdmin salonAdmin = new QSalonAdmin("salonAdmin");

    public final com.project.salon.main.api.domain.common.QSalonCommonBase _super = new com.project.salon.main.api.domain.common.QSalonCommonBase(this);

    public final StringPath adminEmail = createString("adminEmail");

    public final ComparablePath<java.util.UUID> adminGuid = createComparable("adminGuid", java.util.UUID.class);

    public final StringPath adminID = createString("adminID");

    public final StringPath adminName = createString("adminName");

    public final StringPath adminPassword = createString("adminPassword");

    public final StringPath adminPhone = createString("adminPhone");

    public final EnumPath<com.project.salon.main.api.dto.constant.admin.AdminRole> adminRole = createEnum("adminRole", com.project.salon.main.api.dto.constant.admin.AdminRole.class);

    public final StringPath adminType = createString("adminType");

    public final ComparablePath<java.util.UUID> companyGuid = createComparable("companyGuid", java.util.UUID.class);

    public final NumberPath<Long> companySeq = createNumber("companySeq", Long.class);

    //inherited
    public final StringPath descriptionNote = _super.descriptionNote;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = _super.isActive;

    public final DateTimePath<java.time.LocalDateTime> lastDate = createDateTime("lastDate", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QSalonAdmin(String variable) {
        super(SalonAdmin.class, forVariable(variable));
    }

    public QSalonAdmin(Path<? extends SalonAdmin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonAdmin(PathMetadata metadata) {
        super(SalonAdmin.class, metadata);
    }

}

