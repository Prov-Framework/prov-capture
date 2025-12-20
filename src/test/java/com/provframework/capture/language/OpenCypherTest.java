package com.provframework.capture.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.provframework.capture.cypher.CypherLang;
import com.provframework.capture.prov.Bundle;

class OpenCypherTest {
    
    @Test
    void testGetInsertStatement() {
        assertEquals("CREATE (bundle:`Bundle` {generatedAtTime: \"2025-01-25T11:33:10Z\"})",
            CypherLang.getInsertStatement(new Bundle() {{
                setGeneratedAtTime(OffsetDateTime.now());
            }})
        );
    }
}
