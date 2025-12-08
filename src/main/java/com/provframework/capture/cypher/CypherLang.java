package com.provframework.capture.cypher;

import com.provframework.capture.prov.Bundle;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;

public class CypherLang {

    private CypherLang() {
        // static class
    }

    public static String getInsertStatement(Bundle bundle) {
        Node bundleNode = Cypher.node("Bundle")
            .named("bundle")
            .withProperties(
                "generatedAtTime", Cypher.literalOf(bundle.getGeneratedAtTime())
            );

        return Cypher.create(bundleNode).build().getCypher();
    }
}
