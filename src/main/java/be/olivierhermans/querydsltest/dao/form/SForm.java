package be.olivierhermans.querydsltest.dao.form;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;

class SForm extends com.querydsl.sql.RelationalPathBase<SForm> {

    private static final long serialVersionUID = -1966608747;

    public static final SForm form = new SForm("form");

    public final NumberPath<Long> clientId = createNumber("clientId", Long.class);

    public final DateTimePath<java.sql.Timestamp> creationTms = createDateTime("creationTms", java.sql.Timestamp.class);

    public final NumberPath<Long> formId = createNumber("formId", Long.class);

    public final com.querydsl.sql.PrimaryKey<SForm> formPkey = createPrimaryKey(formId);

    public SForm(String variable) {
        super(SForm.class, forVariable(variable), "public", "form");
        addMetadata();
    }

    public SForm(String variable, String schema, String table) {
        super(SForm.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public SForm(String variable, String schema) {
        super(SForm.class, forVariable(variable), schema, "form");
        addMetadata();
    }

    public SForm(Path<? extends SForm> path) {
        super(path.getType(), path.getMetadata(), "public", "form");
        addMetadata();
    }

    public SForm(PathMetadata metadata) {
        super(SForm.class, metadata, "public", "form");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(clientId, ColumnMetadata.named("client_id").withIndex(2).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(creationTms, ColumnMetadata.named("creation_tms").withIndex(3).ofType(Types.TIMESTAMP).withSize(29).withDigits(6).notNull());
        addMetadata(formId, ColumnMetadata.named("form_id").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
    }

}

