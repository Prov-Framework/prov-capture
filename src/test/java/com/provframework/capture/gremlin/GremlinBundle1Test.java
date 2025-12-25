package com.provframework.capture.gremlin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class GremlinBundle1Test {
    @Test
    void testBundle1() throws IOException {
        String actual = new GremlinLangTest().test("bundle1.json");
        assertEquals(actual, """
                === Vertices ===
                Vertex[label=Entity]
                  name=Entity 2
                Vertex[label=Activity]
                  name=Activity 1
                Vertex[label=Entity]
                  name=Entity 1
                Vertex[label=Agent]
                  name=Organization Agent
                Vertex[label=Agent]
                  name=Person Agent
                Vertex[label=Activity]
                  atLocation=42.359780, -71.092070
                  endedAtTime=2025-01-25T11:30:14Z
                  name=Activity 2
                  startedAtTime=2025-01-25T11:30:00Z
                  
                === Edges ===
                Edge[label=wasInformedBy, from=Activity 2, to=Activity 1]
                Edge[label=wasDerivedFrom, from=Entity 2, to=Entity 1]
                Edge[label=actedOnBehalfOf, from=Person Agent, to=Organization Agent]
                Edge[label=wasAttributedTo, from=Entity 2, to=Person Agent]
                Edge[label=wasGeneratedBy, from=Entity 2, to=Activity 2]
                Edge[label=wasAssociatedWith, from=Activity 2, to=Person Agent]
                Edge[label=used, from=Activity 2, to=Entity 1]

                === Summary ===
                Total Vertices: 6
                Total Edges: 7"""
            );
    }    
}

