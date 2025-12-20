package com.provframework.capture.driver;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.neo4j.driver.AuthTokens;

import com.provframework.capture.cypher.CypherDriver;

class BoltTest {

    @Test
    void constructorPropagatesExceptionFromGraphDatabaseDriver() {
        try (MockedStatic<AuthTokens> authTokensStatic = Mockito.mockStatic(AuthTokens.class)) {
            // make AuthTokens.basic throw to simulate a failure before GraphDatabase.driver is called
            authTokensStatic.when(() -> AuthTokens.basic("u", "p")).thenThrow(new IllegalStateException("auth failed"));

            assertThrows(IllegalStateException.class, () -> new CypherDriver("baduri", "u", "p"));
        }
    }
}
