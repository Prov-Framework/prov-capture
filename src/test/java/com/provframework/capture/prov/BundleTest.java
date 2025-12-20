package com.provframework.capture.prov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class BundleTest {

	@Test
	void defaultsAreNull() {
		Bundle b = new Bundle();
		assertNull(b.getEntities());
		assertNull(b.getActivities());
		assertNull(b.getAgents());
		assertNull(b.getGeneratedAtTime());
	}

	@Test
	void settersAndGettersWork() {
		Bundle b = new Bundle();

		Entity e = new Entity();
		Activity a = new Activity();
		Agent ag = new Agent();

		List<Entity> entities = Arrays.asList(e);
		List<Activity> activities = Arrays.asList(a);
		List<Agent> agents = Arrays.asList(ag);

		b.setEntities(entities);
		b.setActivities(activities);
		b.setAgents(agents);
		b.setGeneratedAtTime(OffsetDateTime.now());

		assertEquals(entities, b.getEntities());
		assertEquals(activities, b.getActivities());
		assertEquals(agents, b.getAgents());
		assertEquals(98765L, b.getGeneratedAtTime());
	}

}
