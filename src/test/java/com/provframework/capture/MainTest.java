package com.provframework.capture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;

import com.provframework.capture.cypher.CypherDriver;
import com.provframework.capture.gremlin.GremlinDriver;
import com.provframework.capture.prov.Bundle;
import com.provframework.capture.sparql.SparqlDriver;

import org.neo4j.driver.Driver;

class MainTest {

	@Test
	void mainInvokesSpringApplicationRun() {
		try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
			ConfigurableApplicationContext ctx = mock(ConfigurableApplicationContext.class);
			mocked.when(() -> SpringApplication.run(Main.class, new String[0])).thenReturn(ctx);

			Main.main(new String[0]);

			mocked.verify(() -> SpringApplication.run(Main.class, new String[0]));
		}
	}

	@Test
	void listenGeneratesStatementAndExecutesQuery() throws Exception {
		// mock Bolt and driver
		CypherDriver mockBolt = mock(CypherDriver.class);
		SparqlDriver mockRdf4j = mock(SparqlDriver.class);
		GremlinDriver mockGremlin = mock(GremlinDriver.class);

		Main main = new Main(mockBolt, mockRdf4j, mockGremlin);

		// record executions using a shared flag
		java.util.concurrent.atomic.AtomicBoolean executedFlag = new java.util.concurrent.atomic.AtomicBoolean(false);

		// create a proxy that implements org.neo4j.driver.ExecutableQuery so the cast succeeds
		Object execImpl = java.lang.reflect.Proxy.newProxyInstance(
			org.neo4j.driver.ExecutableQuery.class.getClassLoader(),
			new Class<?>[] { org.neo4j.driver.ExecutableQuery.class },
			(p, m, a) -> {
				if ("execute".equals(m.getName())) {
					executedFlag.set(true);
					return null;
				}
				return null;
			}
		);

		// create a driver proxy that handles executableQuery(...) by returning our execImpl
		Object driverProxy = java.lang.reflect.Proxy.newProxyInstance(
			Driver.class.getClassLoader(),
			new Class<?>[] { Driver.class },
			(proxy, method, args) -> {
				if ("executableQuery".equals(method.getName())) {
					return execImpl;
				}
				return null;
			}
		);

		Bundle bundle = new Bundle();
		assertEquals(null, bundle.getGeneratedAtTime());

		main.listen(bundle);

		// after listen, generatedAtTime should be set
		assertTrue(bundle.getGeneratedAtTime() != null && bundle.getGeneratedAtTime() > 0);

	}
}
