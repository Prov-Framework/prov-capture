package com.provframework.capture.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provframework.capture.prov.Bundle;

import java.io.IOException;

public class BundleDeserializer implements Deserializer<Bundle> {

    private Logger logger = LoggerFactory.getLogger(BundleDeserializer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Bundle deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, Bundle.class);
        } catch (IOException e) {
            logger.error("Error deserializing Bundle", e);
            return null;
        }
    }
}
