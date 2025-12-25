package com.provframework.capture.gremlin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class GremlinBundle2Test {
    @Test
    void testBundle2() throws IOException {
        String actual = new GremlinLangTest().test("bundle2.json");
        assertEquals(actual, """
                === Vertices ===
                Vertex[label=Entity]
                  name=Entity 2
                Vertex[label=Entity]
                  name=Entity 1
                Vertex[label=Agent]
                  name=Person Agent
                Vertex[label=Activity]
                  name=Activity 2

                === Edges ===
                Edge[label=wasDerivedFrom, from=Entity 2, to=Entity 1]
                Edge[label=wasAttributedTo, from=Entity 2, to=Person Agent]
                Edge[label=wasGeneratedBy, from=Entity 2, to=Activity 2]

                === Summary ===
                Total Vertices: 4
                Total Edges: 3"""
            );
    }    
}

