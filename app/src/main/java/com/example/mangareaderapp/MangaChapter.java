package com.example.mangareaderapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MangaChapter {
    private HashMap<String, Object> data;
    private Map<String, String> attributes;
    private List<Map<String, String>> relationships;

    public MangaChapter(HashMap<String, Object> data){
        this.data = data;
        this.attributes = (Map<String, String>) data.get("attributes");
        this.relationships = (List<Map<String, String>>) data.get("relationships");
    }

    public String getId() { return (String) data.get("id"); }

    public String getType() { return (String) data.get("type"); }

    public Map<String, String> getAttributes() { return attributes; }

    public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }

    public String getMangaId() {
        for(Map<String, String> relationship : relationships) {
            if (((String) relationship.get("type")).equals("manga")){
                return (String) relationship.get("id");
            }
        }
        return (String) "NOTFOUND";
    }
}
