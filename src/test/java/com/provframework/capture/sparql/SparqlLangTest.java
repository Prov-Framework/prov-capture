package com.provframework.capture.sparql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.junit.jupiter.api.Test;

import com.provframework.capture.prov.Bundle;

class SparqlLangTest {
    @Test
    void testGetInsertStatement() {
        Bundle bundle = new Bundle();
        bundle.setGeneratedAtTime(1625077800000L);

        InsertDataQuery statement = new SparqlLang().getInsertStatement(bundle);

        Model model = new ModelBuilder().build();
        RDFHandler handler = new StatementCollector(model);
        Repository repository = new SailRepository(new MemoryStore());
        repository.init();
        try (RepositoryConnection connection = repository.getConnection()) {
            connection.prepareUpdate(statement.getQueryString()).execute();
            connection.exportStatements(null, null, null, false, handler);
        }

        assertTrue(model.contains(null, RDF.TYPE, PROV.BUNDLE));
        assertTrue(model.contains(null, PROV.GENERATED_AT_TIME, Values.literal(new BigInteger("1625077800000"))));
    }
}
