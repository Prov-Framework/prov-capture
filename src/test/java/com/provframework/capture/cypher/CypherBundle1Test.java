package com.provframework.capture.cypher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class CypherBundle1Test {
    @Test
    void testBundle1() throws IOException {
        String actual = new CypherLangTest().test("bundle1.json");
        assertEquals(actual, 
            """ 
            MERGE (`Entity 2`:`Entity` {label: 'Entity 2'}) 
            MERGE (`Activity 2`:`Activity` {startedAtTime: '2025-01-25T11:30:00Z', endedAtTime: '2025-01-25T11:30:14Z', atLocation: '42.359780, -71.092070', label: 'Activity 2'}) 
            MERGE (`Person Agent`:`Agent` {label: 'Person Agent'}) 
            MERGE (`Entity 2`)-[:`wasDerivedFrom`]->(`Entity 1`:`Entity` {label: 'Entity 1'}) 
            MERGE (`Entity 2`)-[:`wasAttributedTo`]->(`Person Agent`) 
            MERGE (`Entity 2`)-[:`wasGeneratedBy`]->(`Activity 2`) 
            MERGE (`Activity 2`)-[:`wasAssociatedWith`]->(`Person Agent`) 
            MERGE (`Activity 2`)-[:`used`]->(`Entity 1`) 
            MERGE (`Activity 2`)-[:`wasInformedBy`]->(`Activity 1`:`Activity` {label: 'Activity 1'}) 
            MERGE (`Person Agent`)-[:`actedOnBehalfOf`]->(`Organization Agent`:`Agent` {label: 'Organization Agent'})"""
            .replaceAll("\n", " ")
        );
    }
}
