package com.provframework.capture.language;

import com.provframework.capture.model.Bundle;

public class Sparql {

    private static final String PREFIXES = """
            PREFIX prov: <https://www.w3.org/ns/prov#>
            PREFIX rdf: <https://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX instance: <https://prov.framework/instance#>
            """;

    public static String generateStatement(Bundle bundle) {
        return PREFIXES + """
            INSERT DATA {
                instance:%s rdf:type prov:Bundle .
            }
            """.formatted(bundle.getId());
    }
}
