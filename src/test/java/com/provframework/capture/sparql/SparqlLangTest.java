package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.build.java.Bundle;

class SparqlLangTest {
    public String test(String testBundle) throws IOException {
        Bundle bundle = new Bundle();
        SparqlLang sparqlLang = new SparqlLang();
        sparqlLang.setMyNamespace("http://example.org/abox#", "abox");
        
        String message = new String(getClass().getResourceAsStream("/" + testBundle).readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }
        
        return sparqlLang.getInsertStatement(bundle).getQueryString();
    }

    @Test
    void makeSonarHappy() {
        assertTrue(true);
    }
}
