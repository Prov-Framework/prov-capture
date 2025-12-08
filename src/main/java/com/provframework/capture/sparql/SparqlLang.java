package com.provframework.capture.sparql;

import java.util.UUID;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.sparqlbuilder.core.Prefix;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.GraphPatterns;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf;

import com.provframework.capture.prov.Bundle;

public class SparqlLang {
    
    private static IRI provIRI = Values.iri(PROV.NAMESPACE);
    private static Prefix provPrefix = SparqlBuilder.prefix(PROV.PREFIX, provIRI);

    private static IRI rdfIRI = Values.iri(RDF.NAMESPACE);
    private static Prefix rdfPrefix = SparqlBuilder.prefix(RDF.PREFIX, rdfIRI);

    private static IRI aBoxIRI = Values.iri("http://example.org/abox#");
    private static String aBox = "abox"; // Assertion Box (as apposed to Terminological Box)
    private static Prefix aBoxPrefix = SparqlBuilder.prefix(aBox, aBoxIRI);

    private SparqlLang() {
        // static class
    }

    public static String getInsertStatement(Bundle bundle) {
        return getInsertStatement(bundle, UUID.randomUUID().toString());
    }

    public static String getInsertStatement(Bundle bundle, String bundleUUID) {
        InsertDataQuery statement = new InsertDataQuery();
        statement.prefix(provPrefix);
        statement.prefix(aBoxPrefix);
        statement.prefix(rdfPrefix);

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

        return statement.getQueryString();
    }
}
