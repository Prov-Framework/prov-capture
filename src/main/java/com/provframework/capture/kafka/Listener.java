package com.provframework.capture.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.provframework.capture.model.Bundle;

@Service
public class Listener {
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Bundle> kafkaListenerContainerFactory(
			@NonNull ConsumerFactory<String, Bundle> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Bundle> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

}
