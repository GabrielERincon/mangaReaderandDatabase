package com.example.mangareaderapp;


import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TestMangaDex {

    enum Keys implements JsonKey {
        RESULT("result"),
        DATA("data");

        private final Object value;

        Keys(final Object value) {
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            return this.value;
        }
    }

    @Test
    public void Test01() {
        MangaDex mangaDex = new MangaDex();
        List<Manga> mangas = mangaDex.search_manga();

        /*
        for (Manga manga : mangas) {
            System.out.println("Manga: " + manga);
            System.out.println("\t ... or accesing the title in 2 ways: [" +
                    manga.title() + "] IS THE SAME AS [" +
                    manga.getAttributes().get("title").get("en") + "]");
            System.out.println("\tAttributes for this manga:");
            for (Map.Entry<String, Map<String, Object>> entry : manga.getAttributes().entrySet()) {
                System.out.println("\t" + entry.getKey() + " -> " + entry.getValue());
            }
        }
        */
        mangaDex.get_cover_info(mangas);
        for (Manga manga : mangas) {
            System.out.println("Cover: " + manga);
            /* dozer is needy
            for (MangaCover cover : manga.getCovers()) {
                System.out.println("\tAttributes for the cover with id:" + cover.getId() + " :");
                for (Map.Entry<String, Map<String, Object>> entry : cover.getAttributes().entrySet()) {
                    System.out.println("\t" + entry.getKey() + " -> " + entry.getValue());
                }
             }
            */
        }
    }
}
