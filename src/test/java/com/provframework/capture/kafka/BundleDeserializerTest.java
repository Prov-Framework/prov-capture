package com.provframework.capture.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.provframework.capture.prov.Bundle;

class BundleDeserializerTest {

	@Test
	void deserializeNullReturnsNull() {
		try(BundleDeserializer deser = new BundleDeserializer()){
			Bundle result = deser.deserialize("prov", null);
			assertNull(result);
		}
	}

	@Test
	void deserializeValidJsonReturnsBundle() throws IOException {
		try(BundleDeserializer deser = new BundleDeserializer()) {
			String json = new String(getClass().getResourceAsStream("/bundle.json").readAllBytes());
			Bundle b = deser.deserialize("prov", json.getBytes());
			assertEquals("Entity 2", b.getEntities().get(0).getId());
		}
	}

	@Test
	void deserializeInvalidJsonReturnsNull() {
		try(BundleDeserializer deser = new BundleDeserializer()) {
			byte[] bad = "not-json".getBytes();

			assertEquals(null, deser.deserialize("prov", bad));
		}
	}
}