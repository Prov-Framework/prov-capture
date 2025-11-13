package com.provframework.capture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CaptureApplication {

	/*
	 * The entry point of the system is com.provframework.capture.kafka.Listener.listen().
	 * 
	 * Listener is declared as a Spring Service bean so it automatically starts listening on app startup.
	 */

	public static void main(String[] args) {
		SpringApplication.run(CaptureApplication.class, args);
	}
}
