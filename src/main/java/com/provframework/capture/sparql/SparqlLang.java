package com.provframework.capture.sparql;

import java.util.UUID;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.sparqlbuilder.core.Prefix;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.GraphPatterns;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf;
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

    private static IRI aBoxIRI = Values.iri("http://example.org/abox#");
    private static String aBox = "abox"; // Assertion Box (as apposed to Terminological Box)
    private static Prefix aBoxPrefix = SparqlBuilder.prefix(aBox, aBoxIRI);

    public InsertDataQuery getInsertStatement(Bundle bundle) {
        InsertDataQuery statement = new InsertDataQuery();
        
        insertPrefixes(statement);
        insertBundle(statement, bundle);

        // bundle.getEntities().parallelStream().forEach(entity -> insertEntity(statement, entity));
        // bundle.getActivities().parallelStream().forEach(activity -> insertActivity(statement, activity));
        // bundle.getAgents().parallelStream().forEach(agent -> insertAgent(statement, agent));

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
            Values.iri(aBox + ":" + bundleUUID),
            RDF.TYPE,
            PROV.BUNDLE
        ));
        statement.insertData(GraphPatterns.tp(
            Values.iri(aBox + ":" + bundleUUID),
            PROV.GENERATED_AT_TIME,
            Rdf.literalOf(bundle.getGeneratedAtTime())
        ));
    }

    private void insertEntity(InsertDataQuery statement, Entity entity) {
        
    }

    private void insertActivity(InsertDataQuery statement, Activity activity) {
        
    }

    private void insertAgent(InsertDataQuery statement, Agent agent) {
        
    }
}
