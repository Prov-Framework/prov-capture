package com.provframework.capture.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.provframework.capture.model.Bundle;

class SparqlTest {
    @Test
    void testGenerateStatement() {
        Bundle bundle = new Bundle();
        bundle.setId(UUID.randomUUID().toString());
        
        String expectedStatement = """
            PREFIX prov: <https://www.w3.org/ns/prov#>
            PREFIX rdf: <https://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX instance: <https://prov.framework/instance#>
            INSERT DATA {
                instance:%s rdf:type prov:Bundle .
            }
            """.formatted(bundle.getId());
        String generatedSatement = Sparql.generateStatement(bundle);
        assertEquals(generatedSatement, expectedStatement);
    }    
}
