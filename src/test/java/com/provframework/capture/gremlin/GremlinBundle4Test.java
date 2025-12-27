package com.provframework.capture.gremlin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class GremlinBundle4Test {
    @Test
    void testBundle4() throws IOException {
        String actual = new GremlinLangTest().test("bundle4.json");
        assertEquals(actual, """
                === Vertices ===
                Vertex[label=Entity]
                  name=Entity 1
                Vertex[label=Activity]
                  name=Activity 2
                Vertex[label=Entity]
                  name=Entity 2
                Vertex[label=Agent]
                  name=Person Agent

                === Edges ===
                Edge[label=wasGeneratedBy, from=Entity 1, to=Activity 2]
                Edge[label=wasGeneratedBy, from=Entity 2, to=Activity 2]
                Edge[label=wasAssociatedWith, from=Activity 2, to=Person Agent]

                === Summary ===
                Total Vertices: 4
                Total Edges: 3"""
            );
    }    
}

