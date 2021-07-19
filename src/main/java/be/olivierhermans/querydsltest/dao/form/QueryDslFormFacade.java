package be.olivierhermans.querydsltest.dao.form;

import be.olivierhermans.querydsltest.model.Form;
import be.olivierhermans.querydsltest.service.form.port.out.FormFacade;
import com.querydsl.core.FetchableQuery;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.SQLExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@Primary
class QueryDslFormFacade implements FormFacade {

    private final EntityManager entityManager;
    private final FormEntityMapper mapper;

    @Override
    public Collection<Form> findAll() {
        final SForm form = SForm.form;
        final Expression<Long> rowNumber = SQLExpressions.rowNumber()
                .over()
                .partitionBy(form.clientId)
                .orderBy(form.creationTms.desc()).as("rowNumber");
        final Supplier<QueryBase> querySupplier = () ->
            new JPASQLQuery(entityManager, new PostgreSQLTemplates())
                    .select(form.formId, form.clientId, form.creationTms)
                    .from(SQLExpressions.select(form.formId, form.clientId, form.creationTms, rowNumber)
                            .from(form).where(form.creationTms.lt(localDateToTimestamp(LocalDate.of(2021, 7, 16)))).as(form))
                    .where(Expressions.numberPath(Long.class, "rowNumber").eq(1L));
        final Long numberOfRecords = fetchCount(querySupplier);
        final long pageSize = 1;

        return LongStream.rangeClosed(1, numberOfRecords / pageSize)   // test with non existing pages, returns empty list
                .mapToObj(pageNum ->
                        fetch(querySupplier, pageNum, pageSize).stream()
                                .map(this::mapFormTuple)
                                .map(mapper::entityToModel)
                                .collect(toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private Long fetchCount(Supplier<QueryBase> querySupplier) {
        return ((FetchableQuery) querySupplier.get()).fetchCount();
    }

    private Collection<Tuple> fetch(Supplier<QueryBase> querySupplier, long pageNum, long pageSize) {
        return (Collection<Tuple>) ((FetchableQuery) querySupplier.get().limit(pageSize).offset((pageNum - 1) * pageSize)).fetch();
    }

    private FormEntity mapFormTuple(Tuple tuple) {
        final FormEntity entity = new FormEntity();
        entity.setFormId(tuple.get(SForm.form.formId));
        entity.setClientId(tuple.get(SForm.form.clientId));
        entity.setCreationTms(timestampToOffsetDateTime(tuple.get(SForm.form.creationTms)));
        return entity;
    }

    private OffsetDateTime timestampToOffsetDateTime(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneOffset.ofHours(2));
    }

    private Timestamp localDateToTimestamp(LocalDate localDate) {
        return new Timestamp(localDate.toEpochSecond(LocalTime.of(0, 0), ZoneOffset.UTC));
    }
}
