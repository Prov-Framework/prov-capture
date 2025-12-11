package com.provframework.capture.gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.stereotype.Service;

import com.provframework.capture.prov.Bundle;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

@Service
public class GremlinDriver {
    private GraphTraversalSource g;

    public GremlinDriver() {
        try {
            this.g = traversal().with("conf/remote-graph.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertBundle(Bundle bundle) {
        GremlinLang.getInsertStatement(bundle, g).next();
    }
}
