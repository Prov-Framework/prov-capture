package com.provframework.capture.prov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class EntityTest {

    @Test
    void defaultsAreNull() {
        Entity e = new Entity();
        assertNull(e.getId());
        assertNull(e.getWasDerivedFrom());
        assertNull(e.getWasGeneratedBy());
        assertNull(e.getWasAttributedTo());
    }

    @Test
    void constructorsAndSettersWork() {
        Entity derived = new Entity();
        Activity generated = new Activity();
        Agent attributed = new Agent();

        List<Entity> derivedList = Arrays.asList(derived);
        List<Activity> generatedList = Arrays.asList(generated);
        List<Agent> attributedList = Arrays.asList(attributed);

        Entity e = new Entity("ent-1", derivedList, generatedList, attributedList);
        assertEquals("ent-1", e.getId());
        assertEquals(derivedList, e.getWasDerivedFrom());
        assertEquals(generatedList, e.getWasGeneratedBy());
        assertEquals(attributedList, e.getWasAttributedTo());

        Entity b = new Entity();
        b.setId("b-id");
        b.setWasDerivedFrom(derivedList);
        b.setWasGeneratedBy(generatedList);
        b.setWasAttributedTo(attributedList);

        assertEquals("b-id", b.getId());
        assertEquals(derivedList, b.getWasDerivedFrom());
        assertEquals(generatedList, b.getWasGeneratedBy());
        assertEquals(attributedList, b.getWasAttributedTo());
    }

}

