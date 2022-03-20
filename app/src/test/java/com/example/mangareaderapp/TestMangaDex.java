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

        for (Manga manga : mangas) {
            System.out.println("Manga: " + manga);
        }

        mangaDex.get_cover_info(mangas);
        for (Manga manga : mangas) {
            System.out.println("Manga: " + manga);
            for (MangaCover cover : manga.getCovers()) {
                System.out.println("\tCover:" + cover);
             }
        }
    }
}
