package com.provframework.capture.prov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ActivityTest {

    @Test
    void defaultsAreNull() {
        Activity a = new Activity();
        assertNull(a.getId());
        assertNull(a.getStartedAtTime());
        assertNull(a.getEndedAtTime());
        assertNull(a.getWasInformedBy());
        assertNull(a.getUsed());
        assertNull(a.getWasAssociatedWith());
    }

    @Test
    void constructorsAndSettersWork() {
        List<String> informedList = Arrays.asList("informed");
        List<String> usedList = Arrays.asList("usedEntity");
        List<String> assocList = Arrays.asList("assocAgent");

        Activity a = new Activity("act-1", "2020-01-01T00:00:00Z", "2020-01-01T01:00:00Z", informedList, usedList, assocList);

        assertEquals("act-1", a.getId());
        assertEquals("2020-01-01T00:00:00Z", a.getStartedAtTime());
        assertEquals("2020-01-01T01:00:00Z", a.getEndedAtTime());
        assertEquals(informedList, a.getWasInformedBy());
        assertEquals(usedList, a.getUsed());
        assertEquals(assocList, a.getWasAssociatedWith());

        Activity b = new Activity();
        b.setId("b-id");
        b.setStartedAtTime("s");
        b.setEndedAtTime("e");
        b.setWasInformedBy(informedList);
        b.setUsed(usedList);
        b.setWasAssociatedWith(assocList);

        assertEquals("b-id", b.getId());
        assertEquals("s", b.getStartedAtTime());
        assertEquals("e", b.getEndedAtTime());
        assertEquals(informedList, b.getWasInformedBy());
        assertEquals(usedList, b.getUsed());
        assertEquals(assocList, b.getWasAssociatedWith());
    }

}

