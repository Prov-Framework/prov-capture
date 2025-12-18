package com.provframework.capture.sparql;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.sparqlbuilder.core.Prefix;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.GraphPatterns;
import org.springframework.stereotype.Component;

import com.provframework.capture.prov.Activity;
import com.provframework.capture.prov.Agent;
import com.provframework.capture.prov.Bundle;
import com.provframework.capture.prov.Entity;

@Component
public class SparqlLang {
    
    private static IRI provIRI = Values.iri(PROV.NAMESPACE);
    private static Prefix provPrefix = SparqlBuilder.prefix(PROV.PREFIX, provIRI);

    private static IRI rdfIRI = Values.iri(RDF.NAMESPACE);
    private static Prefix rdfPrefix = SparqlBuilder.prefix(RDF.PREFIX, rdfIRI);

    public static String aBoxNamespace = "http://example.org/abox#";
    private static IRI aBoxIRI = Values.iri(aBoxNamespace);
    private static String aBox = "abox"; // Assertion Box (as apposed to Terminological Box)
    private static Prefix aBoxPrefix = SparqlBuilder.prefix(aBox, aBoxIRI);

    public InsertDataQuery getInsertStatement(Bundle bundle) {
        InsertDataQuery statement = new InsertDataQuery();
        
        insertPrefixes(statement);
        insertBundle(statement, bundle);

        getNonNullStream(bundle.getEntities()).forEach(entity -> insertEntity(statement, entity));
        getNonNullStream(bundle.getActivities()).forEach(activity -> insertActivity(statement, activity));
        getNonNullStream(bundle.getAgents()).forEach(agent -> insertAgent(statement, agent));

        return statement;
    }

    private void insertPrefixes(InsertDataQuery statement) {
        statement.prefix(provPrefix);
        statement.prefix(aBoxPrefix);
        statement.prefix(rdfPrefix);
    }

    private void insertBundle(InsertDataQuery statement, Bundle bundle) {
        String bundleUUID = UUID.randomUUID().toString();

        statement.insertData(GraphPatterns.tp(
            Values.iri(aBoxNamespace, bundleUUID),
            RDF.TYPE,
            PROV.BUNDLE
        ));
        statement.insertData(GraphPatterns.tp(
            Values.iri(aBoxNamespace, bundleUUID),
            PROV.GENERATED_AT_TIME,
            Values.literal(bundle.getGeneratedAtTime())
        ));
    }

    private void insertEntity(InsertDataQuery statement, Entity entity) {
        statement.insertData(GraphPatterns.tp(
            Values.iri(aBoxNamespace, entity.getId()),
            RDF.TYPE,
            PROV.ENTITY    
        ));

        getNonNullStream(entity.getWasDerivedFrom())
        .forEach(derivedFrom -> {
            statement.insertData(GraphPatterns.tp(
                Values.iri(aBoxNamespace, entity.getId()),
                PROV.WAS_DERIVED_FROM,
                Values.iri(aBoxNamespace, derivedFrom.getId())    
            ));
        });

        getNonNullStream(entity.getWasGeneratedBy())
        .forEach(generatedBy -> {
            statement.insertData(GraphPatterns.tp(
                Values.iri(aBoxNamespace, entity.getId()),
                PROV.WAS_GENERATED_BY,
                Values.iri(aBoxNamespace, generatedBy.getId())    
            ));
        });

        getNonNullStream(entity.getWasAttributedTo())
        .forEach(attributedTo -> {
            statement.insertData(GraphPatterns.tp(
                Values.iri(aBoxNamespace, entity.getId()),
                PROV.WAS_ATTRIBUTED_TO,
                Values.iri(aBoxNamespace, attributedTo.getId())    
            ));
        });
    }

    private void insertActivity(InsertDataQuery statement, Activity activity) {
        
    }

    private void insertAgent(InsertDataQuery statement, Agent agent) {
        
    }

    private <T> Stream<T> getNonNullStream(Collection<T> collection) {
        return Stream.ofNullable(collection) // Null list check
        .flatMap(Collection::stream) // Convert to stream
        .filter(Objects::nonNull); // Null element check
    }
}
