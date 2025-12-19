package com.provframework.capture.prov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class AgentTest {

    @Test
    void defaultsAreNull() {
        Agent a = new Agent();
        assertNull(a.getId());
        assertNull(a.getActedOnBehalfOf());
    }

    @Test
    void constructorsAndSettersWork() {
        List<String> actedList = Arrays.asList("subordinate");

        Agent a = new Agent("agent-1", actedList);
        assertEquals("agent-1", a.getId());
        assertEquals(actedList, a.getActedOnBehalfOf());

        Agent b = new Agent();
        b.setId("b-id");
        b.setActedOnBehalfOf(actedList);

        assertEquals("b-id", b.getId());
        assertEquals(actedList, b.getActedOnBehalfOf());
    }

}

