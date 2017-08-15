package de.rgse.timecap.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTimeevent is a Querydsl query type for Timeevent
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTimeevent extends EntityPathBase<Timeevent> {

    private static final long serialVersionUID = 1670455781L;

    public static final QTimeevent timeevent = new QTimeevent("timeevent");

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Calendar> instant = createDateTime("instant", java.util.Calendar.class);

    public final StringPath locationId = createString("locationId");

    public final NumberPath<Long> time = createNumber("time", Long.class);

    public final StringPath userId = createString("userId");

    public QTimeevent(String variable) {
        super(Timeevent.class, forVariable(variable));
    }

    public QTimeevent(Path<? extends Timeevent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimeevent(PathMetadata<?> metadata) {
        super(Timeevent.class, metadata);
    }

}

