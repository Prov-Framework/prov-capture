package com.provframework.capture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class CaptureApplicationTests {

	@Autowired
    KafkaTemplate<String, String> kafkaTemplate;

	@Test
	void listen() {
		String bundle = """
				{
					"id": "bundle 1",
					"entities": [ {
						"id": "Entity 2",
						"wasDerivedFrom": [ {
							"id": "Entity 1"
						}],
						"wasAttributedTo": [ {
							"id": "Person Agent",
							"actedOnBehalfOf": [ {
								"id": "Organization Agent"
							}]
						}],
						"wasGeneratedBy": [ {
							"id": "Activity 2",
							"wasAssociatedWith": [ {
								"id": "Software Agent"
							}],
							"used": [ {
								"id": "Entity 1"
							}]
						}]
					}]
				}
				""";

		kafkaTemplate.send("prov", bundle);
	}

}
