package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import com.provframework.build.java.Bundle;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

class SparqlDriverTest {

    @Test
    void insertBundlePreparesAndExecutesUpdate() throws Exception {
        // prepare mocks
        SparqlLang sparqlLang = mock(SparqlLang.class);
        org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery stmt = mock(org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery.class);
        when(stmt.getQueryString()).thenReturn("INSERT DATA {};");
        when(sparqlLang.getInsertStatement(Mockito.any())).thenReturn(stmt);

        RepositoryConnection connMock = mock(RepositoryConnection.class);
        Update updateMock = mock(Update.class);

        AtomicBoolean executed = new AtomicBoolean(false);
        // when execute is called, set flag
        Mockito.doAnswer(invocation -> {
            executed.set(true);
            return null;
        }).when(updateMock).execute();

        when(connMock.prepareUpdate(anyString())).thenReturn(updateMock);

        // Mock construction of SPARQLRepository so constructor returns our mock connection
        try (MockedConstruction<SPARQLRepository> mocked = Mockito.mockConstruction(SPARQLRepository.class,
                (mockRepo, context) -> {
                    when(mockRepo.getConnection()).thenReturn(connMock);
                })) {

            // build driver (constructor will call new SPARQLRepository(...).getConnection())
            SparqlDriver driver = new SparqlDriver("http://example.org/sparql", "http://example.org/ns#", "ex", sparqlLang);

            // call insertBundle
            driver.insertBundle(new Bundle());

            // assert update executed
            assertTrue(executed.get(), "Expected SPARQL update execute() to be invoked");

            // verify setMyNamespace was invoked on sparqlLang
            verify(sparqlLang).setMyNamespace("http://example.org/ns#", "ex");
            // reference mocked construction to satisfy static analysis
            mocked.constructed();
        }
    }
}
