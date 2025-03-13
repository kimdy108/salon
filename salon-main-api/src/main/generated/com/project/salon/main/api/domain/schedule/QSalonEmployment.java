package com.project.salon.main.api.domain.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonEmployment is a Querydsl query type for SalonEmployment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonEmployment extends EntityPathBase<SalonEmployment> {

    private static final long serialVersionUID = 1857911814L;

    public static final QSalonEmployment salonEmployment = new QSalonEmployment("salonEmployment");

    public final com.project.salon.main.api.domain.common.QSalonCommonBase _super = new com.project.salon.main.api.domain.common.QSalonCommonBase(this);

    public final ComparablePath<java.util.UUID> adminGuid = createComparable("adminGuid", java.util.UUID.class);

    public final NumberPath<Long> adminSeq = createNumber("adminSeq", Long.class);

    //inherited
    public final StringPath descriptionNote = _super.descriptionNote;

    public final EnumPath<com.project.salon.main.api.dto.constant.schedule.EmploymentCategory> employmentCategory = createEnum("employmentCategory", com.project.salon.main.api.dto.constant.schedule.EmploymentCategory.class);

    public final StringPath employmentDate = createString("employmentDate");

    public final ComparablePath<java.util.UUID> employmentGuid = createComparable("employmentGuid", java.util.UUID.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = _super.isActive;

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QSalonEmployment(String variable) {
        super(SalonEmployment.class, forVariable(variable));
    }

    public QSalonEmployment(Path<? extends SalonEmployment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonEmployment(PathMetadata metadata) {
        super(SalonEmployment.class, metadata);
    }

}

