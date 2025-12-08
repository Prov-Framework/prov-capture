package com.provframework.capture.gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.provframework.capture.prov.Bundle;

public class GremlinLang {
    
    private GremlinLang() {
        // Static class
    }

    public static GraphTraversal<Vertex,Vertex> getInsertStatement(Bundle bundle, GraphTraversalSource g) {
        return g.addV("bundle")
            .property("generatedAtTime", bundle.getGeneratedAtTime());
    }
}
