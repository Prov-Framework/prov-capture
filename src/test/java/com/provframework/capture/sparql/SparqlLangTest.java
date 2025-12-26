package com.provframework.capture.sparql;

import java.io.IOException;

import com.provframework.capture.kafka.BundleDeserializer;
import com.provframework.capture.prov.Bundle;

public class SparqlLangTest {
    public String test(String testBundle) throws IOException {
        Bundle bundle = new Bundle();
        SparqlLang sparqlLang = new SparqlLang();
        String message = new String(getClass().getResourceAsStream("/" + testBundle).readAllBytes());
        try(BundleDeserializer deser = new BundleDeserializer()) {
            bundle = deser.deserialize("prov", message.getBytes());
        }
        
        return sparqlLang.getInsertStatement(bundle).getQueryString();
    }
}
