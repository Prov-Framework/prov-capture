package com.provframework.capture.prov;

import java.time.OffsetDateTime;
import java.util.List;

public class Bundle {
    private List<Entity> entities;
    private List<Activity> activities;
    private List<Agent> agents;
    private OffsetDateTime generatedAtTime;

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public OffsetDateTime getGeneratedAtTime() {
        return generatedAtTime;
    }

    public void setGeneratedAtTime(OffsetDateTime generatedAtTime) {
        this.generatedAtTime = generatedAtTime;
    }
}
