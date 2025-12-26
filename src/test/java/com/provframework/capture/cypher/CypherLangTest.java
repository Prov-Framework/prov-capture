package com.provframework.capture.cypher;

import java.io.IOException;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.capture.prov.Bundle;

public class CypherLangTest {
    public String test(String testBundle) throws IOException {
        Bundle bundle = new Bundle();
        CypherLang cypherLang = new CypherLang();
        String message = new String(getClass().getResourceAsStream("/" + testBundle).readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }

        return cypherLang.getInsertStatement(bundle).getCypher();
    }
}
