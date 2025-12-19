package com.provframework.capture.prov;

import java.util.List;

public class Agent {

    private String id; 
    private List<String> actedOnBehalfOf;

    public Agent() {
    }

    public Agent(String id, List<String> actedOnBehalfOf) {
        this.id = id;
        this.actedOnBehalfOf = actedOnBehalfOf;
    }

    public String getId() {
        return id;
    }

    public List<String> getActedOnBehalfOf() {
        return actedOnBehalfOf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActedOnBehalfOf(List<String> actedOnBehalfOf) {
        this.actedOnBehalfOf = actedOnBehalfOf;
    }
}