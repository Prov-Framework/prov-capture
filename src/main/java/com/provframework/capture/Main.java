package com.provframework.capture;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.provframework.capture.driver.Bolt;
import com.provframework.capture.driver.Rdf4j;
import com.provframework.capture.language.OpenCypher;
import com.provframework.capture.language.Sparql;
import com.provframework.capture.prov.Bundle;

@SpringBootApplication
@EnableKafka
@Component
public class Main {

	private Logger logger = LoggerFactory.getLogger(Main.class);

	private Bolt bolt;
	private Rdf4j rdf4j;

	public Main(Bolt bolt, Rdf4j rdf4j) {
		this.bolt = bolt;
		this.rdf4j = rdf4j;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
	public void listen(Bundle bundle) {
		bundle.setGeneratedAtTime(Instant.now().toEpochMilli());
		logger.debug("Received bundle: {}", bundle);

		// String statement = OpenCypher.getInsertStatement(bundle);
		String statement = Sparql.getInsertStatement(bundle);
		logger.debug("Generated Statement: {}", statement);
		// bolt.getDriver().executableQuery(statement).execute();
		rdf4j.getConnection().prepareUpdate(statement).execute();
	}
}