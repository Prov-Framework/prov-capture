package com.provframework.capture.driver;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Rdf4j {
    @SuppressWarnings("unused")
    private final String endpoint;
    @SuppressWarnings("unused")
    private final String username;
    @SuppressWarnings("unused")
    private final String password;
    private final RepositoryConnection connection;

    public Rdf4j(@Value("${sparql.endpoint}") String endpoint,
                @Value("${sparql.username}") String username,
                @Value("${sparql.password}") String password) {
        this.endpoint = endpoint;
        this.username = username;
        this.password = password;
        this.connection = new SPARQLRepository(endpoint, endpoint).getConnection();
    }

    public RepositoryConnection getConnection() {
        return connection;
    }
}
