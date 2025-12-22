package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.OffsetDateTime;

import org.eclipse.rdf4j.common.net.ParsedIRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.capture.prov.Bundle;

class SparqlLangTest {

    Bundle bundle;
    Model model;
    RDFHandler handler;
    SparqlLang sparqlLang;
    static Repository repository = new SailRepository(new MemoryStore());

    @BeforeAll
    static void initRepo() {
        repository.init();
    }

    @BeforeEach
    void setup() {
        this.sparqlLang = new SparqlLang();
    }

    @Test
    void testBundleToSparql() throws IOException {
        String message = new String(getClass().getResourceAsStream("/bundle.json").readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            this.bundle = deser.deserialize("prov", message.getBytes());
        }
        
        InsertDataQuery statement = this.sparqlLang.getInsertStatement(bundle);
        @SuppressWarnings("unused")
        String queryString = statement.getQueryString(); //For debugging
        
        updateModel(statement);

        assertTrue(model.contains(null, RDF.TYPE, PROV.BUNDLE));
        assertTrue(model.contains(null, PROV.GENERATED_AT_TIME, null));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 2").toString()), 
            RDF.TYPE, 
            PROV.ENTITY
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 2").toString()), 
            RDFS.LABEL, 
            Values.literal("Entity 2")
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 1").toString()), 
            RDF.TYPE, 
            PROV.ENTITY
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 1").toString()), 
            RDFS.LABEL, 
            Values.literal("Entity 1")
        ));
        
        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 2").toString()), 
            PROV.WAS_DERIVED_FROM, 
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 1").toString())
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Person Agent").toString()), 
            RDF.TYPE, 
            PROV.AGENT
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Person Agent").toString()), 
            RDFS.LABEL, 
            Values.literal("Person Agent")
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 2").toString()), 
            PROV.WAS_ATTRIBUTED_TO, 
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Person Agent").toString())
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()), 
            RDF.TYPE, 
            PROV.ACTIVITY
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()), 
            RDFS.LABEL, 
            Values.literal("Activity 2")
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 2").toString()), 
            PROV.WAS_GENERATED_BY, 
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString())
        ));

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()),
            PROV.STARTED_AT_TIME,
            Values.literal(OffsetDateTime.parse("2025-01-25T11:30:00Z"))
        )); 

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()),
            PROV.ENDED_AT_TIME,
            Values.literal(OffsetDateTime.parse("2025-01-25T11:30:14Z"))
        )); 

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()),
            PROV.USED,
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Entity 1").toString())
        )); 

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()),
            PROV.WAS_ASSOCIATED_WITH,
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Software Agent").toString())
        )); 

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 2").toString()),
            PROV.WAS_INFORMED_BY,
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Activity 1").toString())
        )); 

        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Person Agent").toString()),
            PROV.ACTED_ON_BEHALF_OF,
            Values.iri(SparqlLang.aBoxNamespace, ParsedIRI.create("Organization Agent").toString())
        )); 
    }

    private void updateModel(InsertDataQuery statement) {
        // Re-initialize model and handler to clear old data
        this.model = new ModelBuilder().build();
        this.handler = new StatementCollector(model);

        try (RepositoryConnection connection = repository.getConnection()) {
            connection.prepareUpdate(statement.getQueryString()).execute();
            connection.exportStatements(null, null, null, false, handler);
        }
    }
}
