package com.example.mangareaderapp;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;
//import com.github.cliftonlabs.json_simple.Jsonable;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MangaDex {
    private URL url;
    private int responseCode = -1;
    private HttpURLConnection con;
    private String raw = "";
    private JsonObject json;
    private String hostname = "api.mangadex.org";
    private int port = 443;

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

    public MangaDex() {
    }

    public MangaDex(String hostname) {
        this.hostname = hostname;
    }

    public MangaDex(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public List<Manga> search_manga() {

        try {
            url = new URL("https", this.hostname, this.port,
                    "/manga?offset=10");
            con = (HttpURLConnection) this.url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() != 200) {
                throw new RuntimeException("Response code: " + con.getResponseCode());
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.json = (JsonObject) Jsoner.deserialize(reader);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        final JsonKey resultKey = Jsoner.mintJsonKey("result", null);
        JsonArray data = json.getCollection(Keys.DATA);
        Mapper mapper = new DozerBeanMapper();
        List<Manga> mangas = new ArrayList<>();
        for (Object dataItem : data) {
            mangas.add(mapper.map(dataItem, Manga.class));
        }

        return mangas;
    }
}
