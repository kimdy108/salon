package com.project.salon.main.api.domain.manage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonCompany is a Querydsl query type for SalonCompany
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonCompany extends EntityPathBase<SalonCompany> {

    private static final long serialVersionUID = -1042665067L;

    public static final QSalonCompany salonCompany = new QSalonCompany("salonCompany");

    public final com.project.salon.main.api.domain.common.QSalonCommonBase _super = new com.project.salon.main.api.domain.common.QSalonCommonBase(this);

    public final StringPath companyAddress = createString("companyAddress");

    public final ComparablePath<java.util.UUID> companyGuid = createComparable("companyGuid", java.util.UUID.class);

    public final StringPath companyName = createString("companyName");

    public final StringPath companyNumber = createString("companyNumber");

    //inherited
    public final StringPath descriptionNote = _super.descriptionNote;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = _super.isActive;

    public final StringPath managerName = createString("managerName");

    public final StringPath managerPhone = createString("managerPhone");

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QSalonCompany(String variable) {
        super(SalonCompany.class, forVariable(variable));
    }

    public QSalonCompany(Path<? extends SalonCompany> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonCompany(PathMetadata metadata) {
        super(SalonCompany.class, metadata);
    }

}

