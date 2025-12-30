package com.provframework.capture.kafka;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import com.provframework.build.java.Bundle;

class ListenerTest {

    @Test
    void kafkaListenerContainerFactorySetsConsumerFactory() {
        Listener listener = new Listener();

        @SuppressWarnings("unchecked")
        ConsumerFactory<String, Bundle> mockConsumerFactory = Mockito.mock(ConsumerFactory.class);

        ConcurrentKafkaListenerContainerFactory<String, Bundle> factory =
                listener.kafkaListenerContainerFactory(java.util.Objects.requireNonNull(mockConsumerFactory));

        assertNotNull(factory);
        assertSame(mockConsumerFactory, factory.getConsumerFactory());
    }
}

