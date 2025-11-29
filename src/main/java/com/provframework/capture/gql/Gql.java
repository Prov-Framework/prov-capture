package com.provframework.capture.gql;

import com.provframework.capture.prov.Bundle;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Statement;

public class Gql {
    public static String generateInsertStatement(Bundle bundle) {
        Node bundleNode = Cypher.node("Bundle")
            .named("bundle")
            .withProperties(
                "generatedAtTime", Cypher.literalOf(bundle.getGeneratedAtTime())
            );

        Statement statement = Cypher.create(bundleNode).build();
        
        return statement.getCypher();
    }
}
