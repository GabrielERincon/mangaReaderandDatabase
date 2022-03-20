package com.example.mangareaderapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Manga {
    private HashMap<String, Object> data;
    private Map<String, Map<String, Object>> attributes;
    private List<Map<String, String>> relationships;
    private List<MangaCover> covers = new ArrayList<>();

    public Manga(HashMap<String, Object> data) {
        this.data = data;
        this.attributes = (Map<String, Map<String, Object>>) data.get("attributes");
        this.relationships = (List<Map<String, String>>) data.get("relationships");
    }

    public String getId() {
        return (String) data.get("id");
    }

    public String getType() {
        return (String) data.get("type");
    }

    public String getTitle() {
        return this.getTitle("en");
    }

    public String getTitle(String locale) {
        return (String) attributes.get("title").get(locale);
    }

    public void addCover(MangaCover cover) {
        this.covers.add(cover);
    }

    public List<MangaCover> getCovers() {
        return this.covers;
    }

    @Override
    public String toString() {
        return String.format("Manga [title=%s, id=%s, (%d covers), data=%s]",
                this.getTitle(), this.getId(), this.getCovers().size(), ""/*this.data*/);
    }
}
