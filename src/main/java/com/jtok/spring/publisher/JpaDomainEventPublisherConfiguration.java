package com.jtok.spring.publisher;

import com.jtok.spring.domainevent.DomainEventProcessor;
import com.jtok.spring.domainevent.DomainEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
//@EntityScan({"com.jtok.spring.domainevent"})
@EnableJpaRepositories("com.jtok.spring.domainevent")
@Slf4j
public class JpaDomainEventPublisherConfiguration {

    @Bean
    @Autowired
    public DomainEventPublisher domainEventExporter(DomainEventRepository repository, KafkaTemplate<String, String> kafkaTemplate, GenericApplicationContext context) {
        return new JpaDomainEventPublisherKafka(repository, kafkaTemplate, context);
    }

    @Bean
    @Autowired
    public DomainEventProcessor domainEventProcessor(DomainEventRepository repository) {
        return new DomainEventProcessor(repository);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, EntityManagerFactoryBuilder builder) {
        log.info(" %%%%%%%%%%%%%%%%%%%%%%% ");
        log.info(" %%%%%%%%%%%%%%%%%%%%%%% ");
        log.info(" building entityManagerFactory ...");
        log.info(" %%%%%%%%%%%%%%%%%%%%%%% ");
        log.info(" %%%%%%%%%%%%%%%%%%%%%%% ");
        return builder
                .dataSource(dataSource)
                .mappingResources("META-INF/orm.xml")
                .build();
    }
}
