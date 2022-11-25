package com.toteuch.compta.backend.utils.cdi.producer;

import org.jooq.*;
import org.jooq.impl.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class DSLContextProducer {

    @Resource(lookup = "java:jboss/datasources/ds")
    private DataSource ds;

    private DefaultConfiguration configuration;

    @PostConstruct
    private void postConstruct() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(ds);
        jooqConfiguration.set(SQLDialect.POSTGRES);
        jooqConfiguration.setRecordMapperProvider(DefaultRecordMapper::new);
        jooqConfiguration.setRecordUnmapperProvider(new RecordUnmapperProvider() {
            @Override
            public <E, R extends Record> RecordUnmapper<E, R> provide(Class<? extends E> type, RecordType<R> recordType) {
                return new DefaultRecordUnmapper<>(type, recordType, jooqConfiguration);
            }
        });
        jooqConfiguration.setTransactionProvider(new NoTransactionProvider());
        configuration = jooqConfiguration;
    }

    @Produces
    @RequestScoped
    @Default
    public DSLContext getContext() {
        return DSL.using(configuration.derive());
    }
}
