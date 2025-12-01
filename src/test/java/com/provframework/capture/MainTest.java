package com.provframework.capture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;

import com.provframework.capture.dbdriver.Bolt;
import com.provframework.capture.prov.Bundle;

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
		Bolt mockBolt = mock(Bolt.class);

		Main main = new Main(mockBolt);

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

		// when getDriver is called, return our proxy (it is assignable to Driver at runtime)
		Mockito.when(mockBolt.getDriver()).thenReturn((Driver) driverProxy);

		// inject the mocked bolt into Main (package-private field)
		Field boltField = Main.class.getDeclaredField("bolt");
		boltField.setAccessible(true);
		boltField.set(main, mockBolt);

		Bundle bundle = new Bundle();
		assertEquals(null, bundle.getGeneratedAtTime());

		main.listen(bundle);

		// after listen, generatedAtTime should be set
		assertTrue(bundle.getGeneratedAtTime() != null && bundle.getGeneratedAtTime() > 0);

		// verify bolt.getDriver() was called
		verify(mockBolt).getDriver();

		// and the exec implementation should have been executed
		assertTrue(executedFlag.get(), "The driver's executable query should have been executed");
	}
}
