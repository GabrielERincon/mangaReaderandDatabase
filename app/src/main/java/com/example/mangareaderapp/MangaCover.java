package com.example.mangareaderapp;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MangaCover {
    private HashMap<String, Object> data;
    private Map<String, String> attributes;
    private List<Map<String, String>> relationships;

    public MangaCover(HashMap<String, Object> data) {
        this.data = data;
        this.attributes = (Map<String, String>) data.get("attributes");
        this.relationships = (List<Map<String, String>>) data.get("relationships");
    }

    public String getId() {
        return (String) data.get("id");
    }

    public String getType() {
        return (String) data.get("type");
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getMangaId() {
        for (Map<String, String> relationship : relationships) {

            if (((String)relationship.get("type")).equals("manga")) {
                return (String)relationship.get("id");
            }
        }
        return (String) "NOTFOUND";
    }

    public String getDescription() {
        return (String) attributes.get("description");
    }

    public String getLocale() {
        return (String) attributes.get("locale");
    }

    public String getFileName() {
        return (String) attributes.get("fileName");
    }

    @Override
    public String toString() {
        return String.format("Cover [id=%s, mangaId=%s, locale=%s, description=%s, filename=%s, data=%s]",
                this.getId(), this.getMangaId(), this.getLocale(), this.getDescription(),
                this.getFileName(), ""/*this.data*/);
    }
}