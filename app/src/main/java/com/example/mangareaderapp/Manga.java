package com.example.mangareaderapp;

import java.io.IOException;
import java.io.StringWriter;
import java.io.SyncFailedException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


public class Manga {
    private String type;
    private String id;
    private Map<String, Map<String, Object>> attributes = new HashMap<>();

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
        System.out.println("setting attributes");
    }

    public String title() {
        return (String) this.attributes.get("title").get("en");
    }

    @Override
    public String toString() {
        return String.format("Manga [title=%s, type=%s, id=%s]",
                this.title(), type, id);
    }
}
