package com.project.salon.main.api.domain.setting;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonStyle is a Querydsl query type for SalonStyle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonStyle extends EntityPathBase<SalonStyle> {

    private static final long serialVersionUID = -325991834L;

    public static final QSalonStyle salonStyle = new QSalonStyle("salonStyle");

    public final com.project.salon.main.api.domain.common.QSalonCommonBase _super = new com.project.salon.main.api.domain.common.QSalonCommonBase(this);

    public final ComparablePath<java.util.UUID> adminGuid = createComparable("adminGuid", java.util.UUID.class);

    public final NumberPath<Long> adminSeq = createNumber("adminSeq", Long.class);

    //inherited
    public final StringPath descriptionNote = _super.descriptionNote;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertDate = _super.insertDate;

    //inherited
    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isActive = _super.isActive;

    public final EnumPath<com.project.salon.main.api.dto.constant.common.IsYesNo> isMiddleTime = createEnum("isMiddleTime", com.project.salon.main.api.dto.constant.common.IsYesNo.class);

    public final StringPath middleTime = createString("middleTime");

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    public final StringPath styleDetail = createString("styleDetail");

    public final NumberPath<Integer> styleDuration = createNumber("styleDuration", Integer.class);

    public final ComparablePath<java.util.UUID> styleGuid = createComparable("styleGuid", java.util.UUID.class);

    public final StringPath styleName = createString("styleName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QSalonStyle(String variable) {
        super(SalonStyle.class, forVariable(variable));
    }

    public QSalonStyle(Path<? extends SalonStyle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonStyle(PathMetadata metadata) {
        super(SalonStyle.class, metadata);
    }

}

