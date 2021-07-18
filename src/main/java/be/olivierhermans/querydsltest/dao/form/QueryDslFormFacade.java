package be.olivierhermans.querydsltest.dao.form;

import be.olivierhermans.querydsltest.dao.SForm;
import be.olivierhermans.querydsltest.model.Form;
import be.olivierhermans.querydsltest.service.form.port.out.FormFacade;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
@Primary
class QueryDslFormFacade implements FormFacade {

    private final JPAQueryFactory jpaQueryFactory;
    private final SQLQueryFactory sqlQueryFactory;
    private final EntityManager entityManager;
    private final FormEntityMapper mapper;

    public QueryDslFormFacade(EntityManager entityManager, DataSource dataSource, FormEntityMapper mapper) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
        this.sqlQueryFactory = new SQLQueryFactory(new Configuration(SQLTemplates.DEFAULT), dataSource);
        this.mapper = mapper;
    }

    @Override
    public Collection<Form> findAll() {
        final SForm form = SForm.form;
        final QFormEntity formEntity = QFormEntity.formEntity;
        final Expression<Long> rowNumber = SQLExpressions.rowNumber()
                .over()
                .partitionBy(form.clientId)
                .orderBy(form.creationTms.desc()).as("rowNumber");
        final JPASQLQuery query = new JPASQLQuery(entityManager, new PostgreSQLTemplates());

        final FetchableQueryBase queryBase =
                (FetchableQueryBase) query.select(form.formId, form.clientId, form.creationTms)
                        .from(SQLExpressions.select(form.formId, form.clientId, form.creationTms, rowNumber)
                                .from(form).as(form))
                        .where(Expressions.numberPath(Long.class, "rowNumber").eq(1L)).where(form.clientId.eq(2L));
        final Collection<Tuple> tuples = (Collection<Tuple>) queryBase.fetch();
        final Collection<FormEntity> entities = tuples.stream().map(tuple -> {
            final FormEntity entity = new FormEntity();
            entity.setFormId(tuple.get(form.formId));
            entity.setClientId(tuple.get(form.clientId));
            entity.setCreationTms(timestampToOffsetDateTime(tuple.get(form.creationTms)));
            return entity;
        }).collect(toList());
        return entities.stream().map(mapper::entityToModel).collect(toList());
    }

    private OffsetDateTime timestampToOffsetDateTime(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneOffset.UTC);
    }
}
