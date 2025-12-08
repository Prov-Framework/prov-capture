package com.provframework.capture.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.provframework.capture.prov.Bundle;
import com.provframework.capture.sparql.SparqlLang;

class SparqlTest {
    @Test
    void testGetInsertStatement() {
        String statement = SparqlLang.getInsertStatement(new Bundle() {{
                setGeneratedAtTime(1625077800000L);
            }}, "b817aa2c-8297-47d0-a599-aab1464c6620");
        assertTrue(statement.contains("PREFIX prov: <http://www.w3.org/ns/prov#>"));
        assertTrue(statement.contains("PREFIX abox: <http://example.org/abox#>"));
        assertTrue(statement.contains("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"));
        assertTrue(statement.contains("INSERT DATA { <abox:b817aa2c-8297-47d0-a599-aab1464c6620> rdf:type prov:Bundle ."));
        assertTrue(statement.contains("<abox:b817aa2c-8297-47d0-a599-aab1464c6620> prov:generatedAtTime 1625077800000 . }"));
    }
}
