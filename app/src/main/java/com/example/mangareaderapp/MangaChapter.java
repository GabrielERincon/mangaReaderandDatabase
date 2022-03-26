package com.example.mangareaderapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MangaChapter {
    private HashMap<String, Object> data;
    private Map<String, String> attributes;
    private List<Map<String, String>> relationships;
    private List<String> pages = new ArrayList<>();
    private String hash;

    public MangaChapter(HashMap<String, Object> data){
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

    public void addPage(String page){
        pages.add(page);
    }

    public List<String> getPages(){
        return pages;
    }

    public void setPages(List<String> pages){
        this.pages = pages;
    }

    public String getHash(){
        return this.hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String getMangaId() {
        for(Map<String, String> relationship : relationships) {
            if (((String) relationship.get("type")).equals("manga")){
                return (String) relationship.get("id");
            }
        }
        return (String) "NOTFOUND";
    }

    public String getChapterId() {
        return (String) data.get("id");
    }

    public String getVolume(){
        return (String) attributes.get("volume");
    }

    public String getChapter(){
        return (String) attributes.get("chapter");
    }

    public String getTranslatedLanguage(){
        return (String) attributes.get("translatedLanguage");
    }

    @Override
    public String toString() {
        return String.format("Chapter [id=%s, Volume=%s, Chapter=%s, Language=%s]", getChapterId(),
                getVolume(), getChapter(), getTranslatedLanguage());
    }
}
