package com.provframework.capture.prov;

import java.util.List;

public class Bundle {
    private List<Entity> entities;
    private List<Activity> activities;
    private List<Agent> agents;
    private Long generatedAtTime;

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

    public Long getGeneratedAtTime() {
        return generatedAtTime;
    }

    public void setGeneratedAtTime(Long generatedAtTime) {
        this.generatedAtTime = generatedAtTime;
    }
}
