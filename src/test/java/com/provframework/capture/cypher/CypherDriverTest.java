package com.provframework.capture.cypher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.provframework.capture.prov.Bundle;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

class CypherDriverTest {

    @Test
    void insertBundleExecutesCypher() {
        // prepare executed flag
        AtomicBoolean executed = new AtomicBoolean(false);

        // create a proxy that implements org.neo4j.driver.ExecutableQuery so the execute() call can be recorded
        Object execImpl = Proxy.newProxyInstance(
            org.neo4j.driver.ExecutableQuery.class.getClassLoader(),
            new Class<?>[] { org.neo4j.driver.ExecutableQuery.class },
            (p, m, a) -> {
                if ("execute".equals(m.getName())) {
                    executed.set(true);
                    return null;
                }
                return null;
            }
        );

        // create a Driver proxy that returns our execImpl for executableQuery(...)
        Object driverProxy = Proxy.newProxyInstance(
            Driver.class.getClassLoader(),
            new Class<?>[] { Driver.class },
            (proxy, method, args) -> {
                if ("executableQuery".equals(method.getName())) {
                    return execImpl;
                }
                return null;
            }
        );

        // mock CypherLang to return a Statement with a cypher string
        CypherLang cypherLang = mock(CypherLang.class);
        org.neo4j.cypherdsl.core.Statement stmt = mock(org.neo4j.cypherdsl.core.Statement.class);
        when(stmt.getCypher()).thenReturn("RETURN 1");
        when(cypherLang.getInsertStatement(Mockito.any())).thenReturn(stmt);

        try (MockedStatic<GraphDatabase> mocked = Mockito.mockStatic(GraphDatabase.class)) {
            // stub GraphDatabase.driver(...) to return our driver proxy
            mocked.when(() -> GraphDatabase.driver(Mockito.anyString(), (org.neo4j.driver.AuthToken) Mockito.any()))
                .thenReturn((Driver) driverProxy);

            // construct CypherDriver (will call GraphDatabase.driver)
            CypherDriver driver = new CypherDriver("bolt://localhost:7687", "u", "p", cypherLang);

            // call insertBundle and assert the execution happened
            driver.insertBundle(new Bundle());
            assertTrue(executed.get(), "Expected driver.executableQuery(...).execute() to be invoked");
        }
    }
}
