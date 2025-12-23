package com.provframework.capture.cypher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.capture.prov.Bundle;

class CypherLangTest {
    @Test
    void testBundleToCypher() throws IOException {
        Bundle bundle = new Bundle();
        CypherLang cypherLang = new CypherLang();
        String message = new String(getClass().getResourceAsStream("/bundle.json").readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }

        String statement = cypherLang.getInsertStatement(bundle).getCypher();

        assertEquals(statement, 
            """ 
            MERGE (`Entity 2`:`Entity` {label: 'Entity 2'})-[:`wasDerivedFrom`]->(`Entity 1`:`Entity` {label: 'Entity 1'}) 
            MERGE (`Entity 2`)-[:`wasAttributedTo`]->(`Person Agent`:`Agent` {label: 'Person Agent'}) 
            MERGE (`Entity 2`)-[:`wasGeneratedBy`]->(`Activity 2`:`Activity` {label: 'Activity 2'}) 
            MERGE (`Activity 2`)-[:`wasAssociatedWith`]->(`Person Agent`) 
            MERGE (`Activity 2`)-[:`used`]->(`Entity 1`) 
            MERGE (`Activity 2`)-[:`wasInformedBy`]->(`Activity 1`:`Activity` {label: 'Activity 1'}) 
            MERGE (`Person Agent`)-[:`actedOnBehalfOf`]->(`Organization Agent`:`Agent` {label: 'Organization Agent'})"""
            .replaceAll("\n", " ")
        );
    }
}
