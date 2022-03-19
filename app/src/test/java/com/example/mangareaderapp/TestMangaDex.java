package com.example.mangareaderapp;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;
//import com.github.cliftonlabs.json_simple.Jsonable;
import org.junit.Assert;
import org.junit.Test;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

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
        MangaDex drive =
                new MangaDex("https://api.mangadex.org/manga?offset=10",
                        "GET");
        JsonObject json = drive.getJson();

        final JsonKey resultKey = Jsoner.mintJsonKey("result", null);
        Assert.assertEquals("ok", json.getString(resultKey));

        // Same but with the key better defined.
        Assert.assertEquals("ok", json.getString(Keys.RESULT));

        JsonArray data = json.getCollection(Keys.DATA);
        Assert.assertTrue("The response should include at least a 10 items (" +
                data.size() + " received)", data.size() >= 10);

        Mapper mapper = new DozerBeanMapper();
        List<Manga> mangas = new ArrayList<>();
        data.forEach((obj) -> {
            mangas.add(mapper.map(obj, Manga.class));
        });

        mangas.forEach((manga) -> {
            System.out.println("Manga toString(): " + manga);
            System.out.println("\t ... or accesing the title in 2 ways: [" +
                    manga.title() + "] IS THE SAME AS [" +
                    manga.getAttributes().get("title").get("en") + "]");
            System.out.println("\tAttributes for this manga:");
            manga.getAttributes().keySet().forEach((key) -> {
                System.out.println("\t" + key + " -> " + manga.getAttributes().get(key));
            });
        });
    }
}
