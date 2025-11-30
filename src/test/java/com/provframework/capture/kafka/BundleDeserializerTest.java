package com.provframework.capture.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.provframework.capture.prov.Bundle;

class BundleDeserializerTest {

	@Test
	void deserializeNullReturnsNull() {
		BundleDeserializer deser = new BundleDeserializer();
		try {
			Bundle result = deser.deserialize("prov", null);
			assertNull(result);
		} finally {
			deser.close();
		}
	}

	@Test
	void deserializeValidJsonReturnsBundle() throws Exception {
		BundleDeserializer deser = new BundleDeserializer();
		try {
			String json = "{\"generatedAtTime\":12345}";
			Bundle b = deser.deserialize("prov", json.getBytes());
			assertEquals(12345L, b.getGeneratedAtTime());
		} finally {
			deser.close();
		}
	}

	@Test
	void deserializeInvalidJsonThrowsRuntimeException() {
		BundleDeserializer deser = new BundleDeserializer();
		try {
			byte[] bad = "not-json".getBytes();

			RuntimeException ex = assertThrows(RuntimeException.class, () -> deser.deserialize("prov", bad));
			assertEquals("Error deserializing Bundle", ex.getMessage());
			// cause should be an IOException (JsonParseException)
			assertEquals(true, ex.getCause() instanceof java.io.IOException);
		} finally {
			deser.close();
		}
	}

}
