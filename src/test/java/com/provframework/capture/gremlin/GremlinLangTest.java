package com.provframework.capture.gremlin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.junit.jupiter.api.Test;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.capture.prov.Bundle;

class GremlinLangTest {
    @Test
    void testBundleToGremlin() throws IOException {
        Bundle bundle = new Bundle();
        GremlinLang gremlinLang = new GremlinLang();
        String message = new String(getClass().getResourceAsStream("/bundle.json").readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }

        // Create an in-memory graph for testing using reflection to handle TinkerGraph
        Graph graph = openTinkerGraph();
        GraphTraversalSource g = graph.traversal();

        // Execute the insert statement
        gremlinLang.getInsertStatement(bundle, g);
        
        // Retrieve all vertices and edges from the graph
        List<Vertex> vertices = g.V().toList();
        List<Edge> edges = g.E().toList();
        
        // Capture Gremlin statements for assertion
        StringBuilder output = new StringBuilder();
        output.append("=== Vertices ===\n");
        vertices.forEach(v -> {
            output.append("Vertex[label=" + v.label() + "]\n");
            StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                    v.properties(), Spliterator.ORDERED), false) // Order for repeatabiltiy
                .sorted((p1, p2) -> p1.key().compareTo(p2.key()))
                .forEachOrdered(p -> 
                    output.append("  " + p.key() + "=" + p.value() + "\n")
                );
        });
        
        output.append("\n=== Edges ===\n");
        edges.forEach(e -> {
            output.append("Edge[label=" + e.label() +
                             ", from=" + e.outVertex().property("name").orElse("N/A") +
                             ", to=" + e.inVertex().property("name").orElse("N/A") + "]\n");
        });
        
        output.append("\n=== Summary ===\n");
        output.append("Total Vertices: " + vertices.size() + "\n");
        output.append("Total Edges: " + edges.size());
        
        // Close resources
        try {
            g.close();
            graph.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // TODO: Add assertions comparing vertices and edges to expected values
        // Example:
        // assertTrue(vertices.size() > 0);
        // assertTrue(edges.stream().anyMatch(e -> e.label().equals("wasGeneratedBy")));

        assertEquals(output.toString(), """
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
    
    private Graph openTinkerGraph() {
        try {
            // Use reflection to instantiate TinkerGraph to avoid import issues
            Class<?> tinkerGraphClass = Class.forName("org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph");
            java.lang.reflect.Method openMethod = tinkerGraphClass.getMethod("open");
            return (Graph) openMethod.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create TinkerGraph", e);
        }
    }    
}

