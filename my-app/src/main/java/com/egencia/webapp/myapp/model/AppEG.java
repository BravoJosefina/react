package com.egencia.webapp.myapp.model;

import com.egencia.library.uitoolkit.model.EG;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppEG extends EG {

    // The namespace you want to use client-side
    @JsonProperty("MyApp")
    private Namespace namespace;

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }
}