package com.project.salon.main.api.domain.setting;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalonSetting is a Querydsl query type for SalonSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalonSetting extends EntityPathBase<SalonSetting> {

    private static final long serialVersionUID = -179349051L;

    public static final QSalonSetting salonSetting = new QSalonSetting("salonSetting");

    public final StringPath settingKey = createString("settingKey");

    public final StringPath settingVal = createString("settingVal");

    public QSalonSetting(String variable) {
        super(SalonSetting.class, forVariable(variable));
    }

    public QSalonSetting(Path<? extends SalonSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalonSetting(PathMetadata metadata) {
        super(SalonSetting.class, metadata);
    }

}

