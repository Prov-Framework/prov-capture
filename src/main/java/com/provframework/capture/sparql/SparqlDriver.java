package com.provframework.capture.sparql;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.provframework.capture.prov.Bundle;

@Service
public class SparqlDriver {
    @SuppressWarnings("unused")
    private final String uri;
    private final RepositoryConnection connection;
    private final SparqlLang sparqlLang;

    public SparqlDriver(@Value("${sparql.uri}") String uri,
                SparqlLang sparqlLang) {
        this.uri = uri;
        this.sparqlLang = sparqlLang;
        this.connection = new SPARQLRepository(uri, uri).getConnection();
    }

    public void insertBundle(Bundle bundle) {
        InsertDataQuery statement = this.sparqlLang.getInsertStatement(bundle);
        connection.prepareUpdate(statement.getQueryString()).execute();
    }
}
