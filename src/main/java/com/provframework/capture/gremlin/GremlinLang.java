package com.provframework.capture.gremlin;

import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.springframework.stereotype.Component;

import com.provframework.capture.StreamUtils;
import com.provframework.capture.prov.Bundle;
import com.provframework.capture.prov.Entity;

@Component
public class GremlinLang {
    public GraphTraversalSource getInsertStatement(Bundle bundle, GraphTraversalSource g) {
        StreamUtils.getNonNullStream(bundle.getEntities()).forEach(entity -> insertEntity(g, entity));
        // StreamUtils.getNonNullStream(bundle.getActivities()).forEach(activity -> insertActivity(g, activity));
        // StreamUtils.getNonNullStream(bundle.getAgents()).forEach(agent -> insertAgent(g, agent));

        return g;
    }

    private void insertEntity(GraphTraversalSource g, Entity entity) {
        g.mergeV(Map.of(T.label, entity.getId())).next();
    }
}
