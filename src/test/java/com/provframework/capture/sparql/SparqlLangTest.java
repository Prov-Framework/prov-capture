package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.capture.prov.Bundle;

class SparqlLangTest {
    @Test
    void testBundleToSparql() throws IOException {
        Bundle bundle = new Bundle();
        SparqlLang sparqlLang = new SparqlLang();
        String message = new String(getClass().getResourceAsStream("/bundle.json").readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }
        
        String statement = sparqlLang.getInsertStatement(bundle).getQueryString();

        assertEquals(statement, 
            """
            PREFIX prov: <http://www.w3.org/ns/prov#>
            PREFIX abox: <http://example.org/abox#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            INSERT DATA { abox:Entity%202 rdf:type prov:Entity .
            abox:Entity%202 rdfs:label "Entity 2"^^xsd:string .
            abox:Entity%201 rdf:type prov:Entity .
            abox:Entity%201 rdfs:label "Entity 1"^^xsd:string .
            abox:Entity%202 prov:wasDerivedFrom abox:Entity%201 .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Entity%202 prov:wasGeneratedBy abox:Activity%202 .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string .
            abox:Entity%202 prov:wasAttributedTo abox:Person%20Agent .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Activity%202 prov:startedAtTime "2025-01-25T11:30:00.0Z"^^xsd:dateTime .
            abox:Activity%202 prov:endedAtTime "2025-01-25T11:30:14.0Z"^^xsd:dateTime .
            abox:Activity%202 prov:atLocation "42.359780, -71.092070"^^xsd:string .
            abox:Entity%201 rdf:type prov:Entity .
            abox:Entity%201 rdfs:label "Entity 1"^^xsd:string .
            abox:Activity%202 prov:used abox:Entity%201 .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string .
            abox:Activity%202 prov:wasAssociatedWith abox:Person%20Agent .
            abox:Activity%201 rdf:type prov:Activity .
            abox:Activity%201 rdfs:label "Activity 1"^^xsd:string .
            abox:Activity%202 prov:wasInformedBy abox:Activity%201 .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string .
            abox:Organization%20Agent rdf:type prov:Agent .
            abox:Organization%20Agent rdfs:label "Organization Agent"^^xsd:string .
            abox:Person%20Agent prov:actedOnBehalfOf abox:Organization%20Agent . }"""
        );
    }
}
