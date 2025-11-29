package com.provframework.capture;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class CaptureApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	// @Autowired
	// KafkaTemplate<String, String> kafkaTemplate;

	// @Test
	// void listen() throws IOException {
	// 	String bundle = new String(getClass().getResourceAsStream("/bundle.json").readAllBytes());
	// 	kafkaTemplate.send("prov", bundle);
	// }

}
