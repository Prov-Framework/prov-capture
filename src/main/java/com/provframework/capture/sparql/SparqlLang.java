package com.provframework.capture.sparql;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.rdf4j.common.net.ParsedIRI;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XSD;
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

    private static IRI rdfsIRI = Values.iri(RDFS.NAMESPACE);
    private static Prefix rdfsPrefix = SparqlBuilder.prefix(RDFS.PREFIX, rdfsIRI);

    private static IRI xsdIRI = Values.iri(XSD.NAMESPACE);
    private static Prefix xsdPrefix = SparqlBuilder.prefix(XSD.PREFIX, xsdIRI);

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
        statement.prefix(rdfsPrefix);
        statement.prefix(xsdPrefix);
    }

    private void insertBundle(InsertDataQuery statement, Bundle bundle) {
        String uuid = UUID.randomUUID().toString();

        statement.insertData(GraphPatterns.tp(
            Values.iri(aBoxNamespace, uuid),
            RDF.TYPE,
            PROV.BUNDLE
        ));
        statement.insertData(GraphPatterns.tp(
            Values.iri(aBoxNamespace, uuid),
            PROV.GENERATED_AT_TIME,
            Values.literal(bundle.getGeneratedAtTime())
        ));
    }

    private void insertEntity(InsertDataQuery statement, Entity entity) {
        String entityIri = insertInstance(statement, entity.getId(), PROV.ENTITY).toString();

        getNonNullStream(entity.getWasDerivedFrom())
        .forEach(derivedFrom -> {
            insertInstance(statement, derivedFrom.getId(), PROV.ENTITY);
            statement.insertData(GraphPatterns.tp(
                Values.iri(aBoxNamespace, entityIri),
                PROV.WAS_DERIVED_FROM,
                Values.iri(aBoxNamespace, ParsedIRI.create(derivedFrom.getId()).toString())    
            ));
        });

        getNonNullStream(entity.getWasGeneratedBy())
        .forEach(generatedBy -> {
            insertInstance(statement, generatedBy.getId(), PROV.ACTIVITY);
            statement.insertData(GraphPatterns.tp(
                Values.iri(aBoxNamespace, entityIri),
                PROV.WAS_GENERATED_BY,
                Values.iri(aBoxNamespace, ParsedIRI.create(generatedBy.getId()).toString())    
            ));
        });

        getNonNullStream(entity.getWasAttributedTo())
        .forEach(attributedTo -> {
            insertInstance(statement, attributedTo.getId(), PROV.AGENT);
            statement.insertData(GraphPatterns.tp(
                Values.iri(aBoxNamespace, entityIri),
                PROV.WAS_ATTRIBUTED_TO,
                Values.iri(aBoxNamespace, ParsedIRI.create(attributedTo.getId()).toString())    
            ));
        });
    }

    private void insertActivity(InsertDataQuery statement, Activity activity) {
        
    }

    private void insertAgent(InsertDataQuery statement, Agent agent) {
        
    }

    /**
     * Handles encoding and inserting client supplied IDs as the URI as well as the 
     * original ID as a label. This small detail is important. A sequence number or
     * a UUID could have been used as a URI, but that could create a entity coreference
     * resolution problem later. If two separate bundles each reference the same agent 
     * using an internal employee number, the desired bahavior is that the relationships
     * from both bundles are added to the same instance. Put another way, there should be
     * one node in the graph, with many edges added to it. If two nodes are added, a need
     * will arise to merge the nodes, which is harder to do after the fact.
     * @param statement
     * @param id
     * @param type
     * @return
     */
    private ParsedIRI insertInstance(InsertDataQuery statement, String id, IRI type) {
        ParsedIRI parsedIri = ParsedIRI.create(id);

        statement.insertData(
            GraphPatterns.tp(
                Values.iri(aBoxNamespace, parsedIri.toString()),
                RDF.TYPE,
                type    
            ),
            GraphPatterns.tp(
                Values.iri(aBoxNamespace, parsedIri.toString()),
                RDFS.LABEL,
                Values.literal(id)
            )
        );

        return parsedIri;
    }

    private <T> Stream<T> getNonNullStream(Collection<T> collection) {
        return Stream.ofNullable(collection) // Null list check
        .flatMap(Collection::stream) // Convert to stream
        .filter(Objects::nonNull); // Null element check
    }
}
