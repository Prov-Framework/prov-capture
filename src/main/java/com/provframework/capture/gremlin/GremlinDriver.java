package com.provframework.capture.gremlin;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.util.MessageSerializer;
import org.apache.tinkerpop.gremlin.util.ser.Serializers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.provframework.capture.prov.Bundle;

@Service
public class GremlinDriver {
    @SuppressWarnings("unused")
    private final String uri;
    @SuppressWarnings("unused")
    private final Integer port;
    private GraphTraversalSource g;

    public GremlinDriver(@Value("${gremlin.uri}") String uri,
                @Value("${gremlin.port}") Integer port) {
        this.uri = uri;
        this.port = port;
        
        try {
            MessageSerializer<?> serializer = Serializers.GRAPHBINARY_V1.simpleInstance(); 
            serializer.configure(
                Map.of("ioRegistries", 
                    List.of("org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry")), Map.of());

            Cluster cluster = Cluster.build()
            .addContactPoint(uri)
            .port(port)
            .serializer(serializer)
            .create(); 
            this.g = traversal().with(DriverRemoteConnection.using(cluster));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertBundle(Bundle bundle) {
        GremlinLang.getInsertStatement(bundle, g).next();
    }
}
