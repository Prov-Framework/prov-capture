package com.provframework.capture.cypher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class CypherBundle2Test {
    @Test
    void testBundle2() throws IOException {
        String actual = new CypherLangTest().test("bundle2.json");
        assertEquals(actual, 
            """ 
            MERGE (`Entity 2`:`Entity` {label: 'Entity 2'}) 
            MERGE (`Entity 2`)-[:`wasDerivedFrom`]->(`Entity 1`:`Entity` {label: 'Entity 1'}) 
            MERGE (`Entity 2`)-[:`wasAttributedTo`]->(`Person Agent`:`Agent` {label: 'Person Agent'}) 
            MERGE (`Entity 2`)-[:`wasGeneratedBy`]->(`Activity 2`:`Activity` {label: 'Activity 2'})"""
            .replaceAll("\n", " ")
        );
    }
}
