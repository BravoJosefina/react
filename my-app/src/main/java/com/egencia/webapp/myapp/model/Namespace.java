package com.egencia.webapp.myapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

public class Namespace {
    @JsonProperty("Models") 
    private Object models = new HashMap<String, Object>();

    @JsonProperty("Presenters") 
    private Object presenters = new HashMap<String, Object>();

    public Object getModels() {
        return models;
    }

    public void setModels(Object models) {
        this.models = models;
    }

    public Object getPresenters() {
        return presenters;
    }

    public void setPresenters(Object presenters) {
        this.presenters = presenters;
    }
}