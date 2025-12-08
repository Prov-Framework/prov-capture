package com.provframework.capture;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.provframework.capture.cypher.CypherBoltDriver;
import com.provframework.capture.cypher.CypherLang;
import com.provframework.capture.gremlin.GremlinLang;
import com.provframework.capture.gremlin.GremlinTinkerpopDriver;
import com.provframework.capture.prov.Bundle;
import com.provframework.capture.sparql.SparqlRestDriver;
import com.provframework.capture.sparql.SparqlLang;

@SpringBootApplication
@EnableKafka
@Component
public class Main {

	private Logger logger = LoggerFactory.getLogger(Main.class);

	private CypherBoltDriver cypherDriver;
	private SparqlRestDriver sparqlDriver;
	private GremlinTinkerpopDriver gremlinDriver;

	public Main(CypherBoltDriver cypherDriver, 
		SparqlRestDriver sparqlDriver, 
		GremlinTinkerpopDriver gremlinDriver) {
			this.cypherDriver = cypherDriver;
			this.sparqlDriver = sparqlDriver;
			this.gremlinDriver = gremlinDriver;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
	public void listen(Bundle bundle) {
		bundle.setGeneratedAtTime(Instant.now().toEpochMilli());
		logger.debug("Received bundle: {}", bundle);

		// Cypher
		// String statement = CypherLang.getInsertStatement(bundle);
		// cypherDriver.getDriver().executableQuery(statement).execute();

		// SPARQL
		// String statement = SparqlLang.getInsertStatement(bundle);
		// sparqlDriver.getConnection().prepareUpdate(statement).execute();

		// Gremlin
		GremlinLang.getInsertStatement(bundle, gremlinDriver.getGraphTraversalSource()).next();
	}
}