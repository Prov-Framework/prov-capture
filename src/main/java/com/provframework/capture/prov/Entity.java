package com.provframework.capture.prov;

import java.util.List;

public class Entity {

    private String id;
    private List<String> wasDerivedFrom;
    private List<String> wasGeneratedBy;
    private List<String> wasAttributedTo;
    
    public Entity() {
    }

    public Entity(String id, List<String> wasDerivedFrom, List<String> wasGeneratedBy, List<String> wasAttributedTo) {
        this.id = id;
        this.wasDerivedFrom = wasDerivedFrom;
        this.wasGeneratedBy = wasGeneratedBy;
        this.wasAttributedTo = wasAttributedTo;
    }

    public String getId() {
        return id;
    }  

    public List<String> getWasDerivedFrom() {
        return wasDerivedFrom;
    }

    public List<String> getWasGeneratedBy() {
        return wasGeneratedBy;
    }

    public List<String> getWasAttributedTo() {
        return wasAttributedTo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWasDerivedFrom(List<String> wasDerivedFrom) {
        this.wasDerivedFrom = wasDerivedFrom;
    }  

    public void setWasAttributedTo(List<String> wasAttributedTo) {
        this.wasAttributedTo = wasAttributedTo;
    }

    public void setWasGeneratedBy(List<String> wasGeneratedBy) {
        this.wasGeneratedBy = wasGeneratedBy;
    }
}
