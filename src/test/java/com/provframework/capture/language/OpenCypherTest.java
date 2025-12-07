package com.provframework.capture.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.provframework.capture.prov.Bundle;

class OpenCypherTest {
    
    @Test
    void testGetInsertStatement() {
        assertEquals("CREATE (bundle:`Bundle` {generatedAtTime: 1625077800000})",
            OpenCypher.getInsertStatement(new Bundle() {{
                setGeneratedAtTime(1625077800000L);
            }})
        );
    }
}
