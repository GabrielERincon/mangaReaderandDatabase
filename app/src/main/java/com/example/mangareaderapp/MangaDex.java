package com.example.mangareaderapp;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class MangaDex {
    private int responseCode = -1;
    private String raw = "";
    private JsonObject json;
    private String apiHostname = "api.mangadex.org";
    private int apiPort = 443;
    private String dlHostname = "uploads.mangadex.org";
    private int dlPort = 443;

    enum Keys implements JsonKey {
        RESULT("result"),
        DATA("data");

        private Object value;

        Keys(Object value) {
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

    public MangaDex(String apiHostname, String dlHostname) {
        this.apiHostname = apiHostname;
        this.dlHostname = dlHostname;
    }

    public MangaDex(String apiHostname, int apiPort, String dlHostname, int dlPort) {
        this.apiHostname = apiHostname;
        this.apiPort = apiPort;
        this.dlHostname = dlHostname;
        this.dlPort = dlPort;
    }

    public List<Manga> search_manga(String pattern) {
        URL url;
        HttpURLConnection con;
        pattern.replace(" ", "-");
        /* TODO: This needs to loop over the json responses and if needed ask for more
           entries using the returned offset.
         */
        try {
            url = new URL("https", this.apiHostname, this.apiPort,
                    "/manga?limit=50&title=" + URLEncoder.encode(pattern, StandardCharsets.UTF_8.toString()));
            System.out.println("URL : " + url);
            con = (HttpURLConnection) url.openConnection();
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

        //final JsonKey resultKey = Jsoner.mintJsonKey("result", null);
        //System.out.println("Json : " + json);
        JsonArray data = json.getCollection(Keys.DATA);
        List<Manga> mangas = new ArrayList<>();
        for (Object dataItem : data) {
            Manga manga = new Manga((HashMap<String, Object>)dataItem);
            mangas.add(manga);
        }

        return mangas;
    }

    public void get_cover_info(List<Manga> mangas) {
        URL url;
        HttpURLConnection con;

        /* Build the query string using the ids for all the Manga objects received. */
        StringBuilder queryString = new StringBuilder("/cover?limit=100");
        for (Manga manga : mangas) {
            queryString.append("&manga[]=");
            queryString.append(manga.getId());
        }

        // TODO: Implement looping for total search bigger than limit
        try {
            url = new URL("https", this.apiHostname, this.apiPort, queryString.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() != 200) {
                throw new RuntimeException("Response code (get_cover_info): " + con.getResponseCode());
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.json = (JsonObject) Jsoner.deserialize(reader);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JsonArray data = json.getCollection(Keys.DATA);

        List<MangaCover> covers = new ArrayList();
        for (Object dataItem : data) {
            MangaCover cover = new MangaCover((HashMap<String, Object>)dataItem);
            covers.add(cover);
            /* TODO Improve matching the covers to the Mangas provided */
            for (Manga manga : mangas) {
                if (manga.getId().equals(cover.getMangaId())) {
                    manga.addCover(cover);
                }
            }
        }
    }

    public ReadableByteChannel stream_cover(MangaCover cover) {
        StringBuilder queryString;
        ReadableByteChannel readChannel;
        URL url;

        try {
            queryString = new StringBuilder("/covers/");
            queryString.append(cover.getMangaId());
            queryString.append("/");
            queryString.append(cover.getFileName());
            url = new URL("https", this.dlHostname, this.dlPort, queryString.toString());
            readChannel = Channels.newChannel(url.openStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error reading cover file" + e.getMessage());
        }
        return readChannel;
    }
}
