package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class SparqlBundle4Test {
    @Test
    void testBundle4() throws IOException {
        String actual = new SparqlLangTest().test("bundle4.json");
        assertEquals(actual, 
            """
            PREFIX prov: <http://www.w3.org/ns/prov#>
            PREFIX abox: <http://example.org/abox#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            INSERT DATA { abox:Entity%201 rdf:type prov:Entity .
            abox:Entity%201 rdfs:label "Entity 1"^^xsd:string .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Entity%201 prov:wasGeneratedBy abox:Activity%202 .
            abox:Entity%202 rdf:type prov:Entity .
            abox:Entity%202 rdfs:label "Entity 2"^^xsd:string .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Entity%202 prov:wasGeneratedBy abox:Activity%202 .
            abox:Activity%202 rdf:type prov:Activity .
            abox:Activity%202 rdfs:label "Activity 2"^^xsd:string .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string .
            abox:Activity%202 prov:wasAssociatedWith abox:Person%20Agent .
            abox:Person%20Agent rdf:type prov:Agent .
            abox:Person%20Agent rdfs:label "Person Agent"^^xsd:string . }"""
        );
    }
}
