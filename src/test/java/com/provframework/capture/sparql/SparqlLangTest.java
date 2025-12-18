package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
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

import com.provframework.capture.prov.Bundle;
import com.provframework.capture.prov.Entity;

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
        this.bundle = new Bundle();
        this.bundle.setGeneratedAtTime(1625077800000L);
        this.sparqlLang = new SparqlLang();
    }

    @Test
    void testBundleOnly() {
        bundle.setGeneratedAtTime(1625077800000L);

        InsertDataQuery statement = this.sparqlLang.getInsertStatement(bundle);
        @SuppressWarnings("unused")
        String queryString = statement.getQueryString(); //For debugging

        updateModel(statement);

        assertTrue(model.contains(null, RDF.TYPE, PROV.BUNDLE));
        assertTrue(model.contains(null, PROV.GENERATED_AT_TIME, Values.literal(1625077800000L)));
    }

    @Test
    void testEntity() {
        Entity entity = new Entity();
        entity.setId("entity1");

        bundle.setEntities(List.of(entity));

        InsertDataQuery statement = this.sparqlLang.getInsertStatement(bundle);
        @SuppressWarnings("unused")
        String queryString = statement.getQueryString(); //For debugging
        
        updateModel(statement);
        
        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, entity.getId()), 
            RDF.TYPE, 
            PROV.ENTITY
        ));
    }

    @Test
    void testDerivedEntity() {
        Entity entity = new Entity();
        entity.setId("entity1");

        Entity entity2 = new Entity();
        entity2.setId("entity2");

        entity2.setWasDerivedFrom(List.of(entity));

        bundle.setEntities(List.of(entity2));

        InsertDataQuery statement = this.sparqlLang.getInsertStatement(bundle);
        @SuppressWarnings("unused")
        String queryString = statement.getQueryString(); //For debugging
        
        updateModel(statement);
        
        assertTrue(model.contains(
            Values.iri(SparqlLang.aBoxNamespace, entity2.getId()), 
            PROV.WAS_DERIVED_FROM, 
            Values.iri(SparqlLang.aBoxNamespace, entity.getId())
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
