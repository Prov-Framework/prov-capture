package com.provframework.capture.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import com.provframework.capture.model.Bundle;

@Service
public class Listener {

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
	public void listen(Bundle bundle) {
		System.out.println(bundle.getId());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Bundle> kafkaListenerContainerFactory(
			ConsumerFactory<String, Bundle> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Bundle> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
    
}
