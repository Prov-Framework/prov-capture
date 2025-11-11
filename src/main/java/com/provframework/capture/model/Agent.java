package com.provframework.capture.model;

import java.util.List;

public class Agent {

    private String id; 
    private List<Agent> actedOnBehalfOf;

    public Agent() {
    }

    public Agent(String id, List<Agent> actedOnBehalfOf) {
        this.id = id;
        this.actedOnBehalfOf = actedOnBehalfOf;
    }

    public String getId() {
        return id;
    }

    public List<Agent> getActedOnBehalfOf() {
        return actedOnBehalfOf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActedOnBehalfOf(List<Agent> actedOnBehalfOf) {
        this.actedOnBehalfOf = actedOnBehalfOf;
    }
}