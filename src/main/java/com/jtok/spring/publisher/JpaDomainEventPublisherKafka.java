package com.jtok.spring.publisher;

import com.jtok.spring.domainevent.DomainEvent;
import com.jtok.spring.domainevent.DomainEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaDomainEventPublisherKafka extends DomainEventPublisherKafkaSupport {

    @Autowired
    public JpaDomainEventPublisherKafka(DomainEventRepository repository, KafkaTemplate<String, String> kafkaTemplate, GenericApplicationContext context) {
        super(repository, kafkaTemplate, context);
    }

    @Override
    void handlePublishingFailure(Throwable ex) {
        throw new RuntimeException("Error sending data to kafka: " + ex.getMessage(), ex);
    }

    @Override
    void handlePublishingSuccess(DomainEvent event) {
    }

    @Override
    void completePublishing(List<DomainEvent> events) {
    }
}
