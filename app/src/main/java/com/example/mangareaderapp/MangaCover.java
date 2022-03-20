package com.example.mangareaderapp;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class MangaCover {
    private String type;
    private String id;
    private Map<String, Map<String, Object>> attributes = new HashMap<>();
    private List<Object> relationships = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Map<String, Object>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Map<String, Object>> attributes) {
        this.attributes = attributes;
    }

    public List<Object> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Object> relationships) {
        this.relationships = relationships;
    }

    @Override
    public String toString() {
        return String.format("Cover [type=%s, id=%s]",
                type, id);
    }
}