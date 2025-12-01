package com.provframework.capture.gql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.provframework.capture.prov.Bundle;

class GqlTest {
    
    @Test
    void testGetInsertStatement() {
        assertEquals("CREATE (bundle:`Bundle` {generatedAtTime: 1625077800000})",
            Gql.getInsertStatement(new Bundle() {{
                setGeneratedAtTime(1625077800000L);
            }})
        );
    }
}
