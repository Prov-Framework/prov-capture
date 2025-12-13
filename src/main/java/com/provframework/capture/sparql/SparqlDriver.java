package com.provframework.capture.sparql;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.provframework.capture.prov.Bundle;

@Service
public class SparqlDriver {
    @SuppressWarnings("unused")
    private final String uri;
    private final RepositoryConnection connection;

    public SparqlDriver(@Value("${sparql.uri}") String uri) {
        this.uri = uri;
        this.connection = new SPARQLRepository(uri, uri).getConnection();
    }

    public void insertBundle(Bundle bundle) {
        String statement = SparqlLang.getInsertStatement(bundle);
        connection.prepareUpdate(statement).execute();
    }
}
