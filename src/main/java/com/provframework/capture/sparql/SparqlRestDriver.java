package com.provframework.capture.sparql;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SparqlRestDriver {
    @SuppressWarnings("unused")
    private final String endpoint;
    private final RepositoryConnection connection;

    public SparqlRestDriver(@Value("${sparql.endpoint}") String endpoint) {
        this.endpoint = endpoint;
        this.connection = new SPARQLRepository(endpoint, endpoint).getConnection();
    }

    public RepositoryConnection getConnection() {
        return connection;
    }
}
