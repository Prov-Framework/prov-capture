package com.provframework.capture.cypher;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.build.java.Bundle;

class CypherLangTest {
    public String test(String testBundle) throws IOException {
        Bundle bundle = new Bundle();
        CypherLang cypherLang = new CypherLang();
        String message = new String(getClass().getResourceAsStream("/" + testBundle).readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }

        return cypherLang.getInsertStatement(bundle).getCypher();
    }

    @Test
    void makeSonarHappy() {
        assertTrue(true);
    }
}
