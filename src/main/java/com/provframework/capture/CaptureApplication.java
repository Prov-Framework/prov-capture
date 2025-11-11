package com.provframework.capture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.provframework.capture.model.Bundle;

@SpringBootApplication
@EnableKafka
public class CaptureApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptureApplication.class, args);
	}

	@KafkaListener(topics = "prov", groupId = "capture")
	public void listen(Bundle bundle) {
		System.out.println(bundle.getId());
	}

}
