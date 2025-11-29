package com.provframework.capture.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.provframework.capture.prov.Bundle;

import java.io.IOException;

public class BundleDeserializer implements Deserializer<Bundle> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Bundle deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, Bundle.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing Bundle", e);
        }
    }
}
