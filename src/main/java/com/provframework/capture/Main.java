package com.provframework.capture;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.provframework.capture.model.Bundle;

@SpringBootApplication
@EnableKafka
public class Main {

	Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
	public void listen(Bundle bundle) {
		bundle.setId(UUID.randomUUID().toString());
		bundle.setGeneratedAtTime(Instant.now().toEpochMilli());
		logger.info("Captured bundle: {}", bundle);
	}

}
