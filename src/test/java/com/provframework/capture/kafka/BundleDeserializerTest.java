package com.provframework.capture.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
	void deserializeValidJsonReturnsBundle() {
		try(BundleDeserializer deser = new BundleDeserializer()) {
			String json = "{\"generatedAtTime\":12345}";
			Bundle b = deser.deserialize("prov", json.getBytes());
			assertEquals(12345L, b.getGeneratedAtTime());
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