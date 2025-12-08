package com.provframework.capture.driver;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;

import com.provframework.capture.cypher.CypherBoltDriver;

class BoltTest {

    @Test
    void constructorInitializesDriver() {
        // Construct Bolt with sample values; GraphDatabase.driver will create a Driver instance.
        CypherBoltDriver bolt = new CypherBoltDriver("bolt://localhost:7687", "user", "pass");

        // driver should be non-null (constructed successfully)
        Driver driver = bolt.getDriver();
        org.junit.jupiter.api.Assertions.assertNotNull(driver);
    }

    @Test
    void constructorPropagatesExceptionFromGraphDatabaseDriver() {
        try (MockedStatic<AuthTokens> authTokensStatic = Mockito.mockStatic(AuthTokens.class)) {
            // make AuthTokens.basic throw to simulate a failure before GraphDatabase.driver is called
            authTokensStatic.when(() -> AuthTokens.basic("u", "p")).thenThrow(new IllegalStateException("auth failed"));

            assertThrows(IllegalStateException.class, () -> new CypherBoltDriver("baduri", "u", "p"));
        }
    }
}
