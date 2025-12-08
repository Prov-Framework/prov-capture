package com.provframework.capture.gremlin;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.stereotype.Service;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

@Service
public class GremlinTinkerpopDriver {
    private GraphTraversalSource g;

    public GremlinTinkerpopDriver() {
        try {
            this.g = traversal().withRemote("conf/remote-graph.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GraphTraversalSource getGraphTraversalSource() {
        return this.g;
    }
}
