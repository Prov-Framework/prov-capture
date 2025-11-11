package com.provframework.capture.model;

import java.util.List;

public class Bundle {
    private String id;
    private List<Entity> entities;
    private List<Activity> activities;
    private List<Agent> agents;
    private Long generatedAtTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
