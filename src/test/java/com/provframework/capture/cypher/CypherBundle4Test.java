package com.provframework.capture.cypher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class CypherBundle4Test {
    @Test
    void testBundle4() throws IOException {
        String actual = new CypherLangTest().test("bundle4.json");
        assertEquals(actual, 
            """ 
            MERGE (`Entity 1`:`Entity` {label: 'Entity 1'}) 
            MERGE (`Entity 2`:`Entity` {label: 'Entity 2'}) 
            MERGE (`Activity 2`:`Activity` {label: 'Activity 2'}) 
            MERGE (`Person Agent`:`Agent` {label: 'Person Agent'}) 
            MERGE (`Entity 1`)-[:`wasGeneratedBy`]->(`Activity 2`) 
            MERGE (`Entity 2`)-[:`wasGeneratedBy`]->(`Activity 2`) 
            MERGE (`Activity 2`)-[:`wasAssociatedWith`]->(`Person Agent`)"""
            .replaceAll("\n", " ")
        );
    }
}
