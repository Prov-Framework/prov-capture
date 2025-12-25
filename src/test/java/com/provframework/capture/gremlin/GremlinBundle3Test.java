package com.provframework.capture.gremlin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class GremlinBundle3Test {
    @Test
    void testBundle3() throws IOException {
        String actual = new GremlinLangTest().test("bundle3.json");
        assertEquals(actual, """
                === Vertices ===
                Vertex[label=Entity]
                  name=Entity 2
                Vertex[label=Agent]
                  name=Person Agent
                Vertex[label=Activity]
                  name=Activity 2
                  startedAtTime=2025-01-25T11:30:00Z
                Vertex[label=Entity]
                  name=Entity 1

                === Edges ===
                Edge[label=wasAttributedTo, from=Entity 2, to=Person Agent]
                Edge[label=wasGeneratedBy, from=Entity 2, to=Activity 2]
                Edge[label=wasAssociatedWith, from=Activity 2, to=Person Agent]
                Edge[label=used, from=Activity 2, to=Entity 1]

                === Summary ===
                Total Vertices: 4
                Total Edges: 4"""
            );
    }    
}

