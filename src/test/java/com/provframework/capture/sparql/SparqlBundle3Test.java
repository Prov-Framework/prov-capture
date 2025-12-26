package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class SparqlBundle3Test {
    @Test
    void testBundle3() throws IOException {
        String actual = new SparqlLangTest().test("bundle3.json");
        assertEquals(actual, 
            """
            PREFIX prov: <http://www.w3.org/ns/prov#>
            PREFIX abox: <http://example.org/abox#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            INSERT DATA { abox:Entity%202 rdf:type prov:Entity .
            abox:Entity%202 rdfs:label "Entity 2"^^xsd:string .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Entity%202 prov:wasGeneratedBy abox:Activity%202 .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string .
            abox:Entity%202 prov:wasAttributedTo abox:Person%20Agent .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Activity%202 prov:startedAtTime "2025-01-25T11:30:00.0Z"^^xsd:dateTime .
            abox:Entity%201 rdf:type prov:Entity .
            abox:Entity%201 rdfs:label "Entity 1"^^xsd:string .
            abox:Activity%202 prov:used abox:Entity%201 .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string .
            abox:Activity%202 prov:wasAssociatedWith abox:Person%20Agent .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string . }"""
        );
    }
}
