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
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MangaDex {
    private int responseCode = -1;
    private String raw = "";
    private JsonObject json;
    private String apiHostname = "api.mangadex.org";
    private int apiPort = 443;
    private String dlHostname = "uploads.mangadex.org";
    private int dlPort = 443;
    private Map<String, String> tags;

    //Defines JsonKeys for usage in api calls
    enum Keys implements JsonKey {
        RESULT("result"),
        DATA("data"),
        LIMIT("limit"),
        OFFSET("offset"),
        TOTAL("total"),
        CHAPTER("chapter");

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

    //No paramater contructor
    public MangaDex() {
        getTagInfo();
    }

    //Construtor in the case of defining separate host names. Shouldn't be used for this project
    public MangaDex(String apiHostname, String dlHostname) {
        this.apiHostname = apiHostname;
        this.dlHostname = dlHostname;
    }

    //Same as previous but with explicit ports as well
    public MangaDex(String apiHostname, int apiPort, String dlHostname, int dlPort) {
        this.apiHostname = apiHostname;
        this.apiPort = apiPort;
        this.dlHostname = dlHostname;
        this.dlPort = dlPort;
    }

    /*Populates the tag map. Called in the contructor so you shouldn't need to call it unless
    to refresh list*/
    public void getTagInfo(){
        tags = new HashMap<String, String>();
        URL url;
        HttpURLConnection con;
        StringBuilder queryString = new StringBuilder("/manga/tag");

        try {
            url = new URL("https", this.apiHostname, this.apiPort, queryString.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if(con.getResponseCode() != 200){
                throw new RuntimeException("Response code (getTags): " + con.getResponseCode());
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.json = (JsonObject) Jsoner.deserialize(reader);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        JsonArray data = json.getCollection(Keys.DATA);

        for(Object dataItem : data){
            Map<String, Map<String, Map<String, String>>> dataMap = (Map<String, Map<String, Map<String, String>>>) dataItem;
            tags.put(dataMap.get("attributes").get("name").get("en"), (String) ((JsonObject) dataItem).get("id"));
        }
    }

    //Get the tag map
    public Map<String, String> getTags(){
        return tags;
    }

    //Get list of manga by tag. Use the tag ids in the tag map
    public List<Manga> searchByTag(String tagId){
        return searchManga(null, tagId);
    }

    /*Get list of generic manga. These will literally be the first that the api returns, I don't
    know what determines this list.*/
    public List<Manga> searchManga() {
        return searchManga(null, null);
    }

    //Get list of manga by title fitting the provided pattern
    public List<Manga> searchManga(String pattern) {
        return searchManga(pattern, null);
    }

    //Back bone of the search manga line. Use if you need to specify pattern and tag
    public List<Manga> searchManga(String pattern, String tagId) {
        URL url;
        HttpURLConnection con;
        StringBuilder queryString = new StringBuilder("/manga");

        try {
            pattern = URLEncoder.encode(pattern, "UTF-8");

            if(pattern != null && tagId != null){
                queryString.append("?title=" + pattern + "&includedTags[]=" + tagId);
            } else if(pattern != null){
                queryString.append("?title=" + pattern);
            } else if(tagId != null){
                queryString.append("?includedTags[]=" + tagId);
            }

            /* TODO: This needs to loop over the json responses and if needed ask for more
            entries using the returned offset.
            */
            url = new URL("https", this.apiHostname, this.apiPort, queryString.toString());
            //System.out.println("URL: " + url);

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
            System.out.println("Exception in url call: " + e.getMessage());
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

    /*For every manga in the list of mangas, it gets the available covers. See TestCovers
    in the JUnit tests to see how to retrieve a cover.*/
    public void getCoverInfo(List<Manga> mangas) {
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

        //List<MangaCover> covers = new ArrayList();
        for (Object dataItem : data) {
            MangaCover cover = new MangaCover((HashMap<String, Object>)dataItem);
            //covers.add(cover);
            /* TODO Improve matching the covers to the Mangas provided */
            for (Manga manga : mangas) {
                if (manga.getId().equals(cover.getMangaId())) {
                    manga.addCover(cover);
                }
            }
        }
    }

    //Populates the chapter list for the provided manga
    public void getChapterInfo(Manga manga){
        URL url;
        HttpURLConnection con;
        int offset = 0;
        int limit = 100;

        do {
            /* Build the query string using the ids for all the Manga objects received. */
            StringBuilder queryString = new StringBuilder("/chapter?manga=" + manga.getId() + "&offset="
                    + offset + "&limit=" + limit + "&translatedLanguage[]=en");

            try {
                url = new URL("https", this.apiHostname, this.apiPort, queryString.toString());
                System.out.println(url);
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

            for (Object dataItem : data) {
                manga.addChapter(new MangaChapter((HashMap<String, Object>) dataItem));
            }

            offset += limit;
        } while(json.getInteger(Keys.TOTAL) > json.getInteger(Keys.LIMIT) + json.getInteger(Keys.OFFSET));
    }

    /*Populates the pages list for the provided chapter. Note that the list is the file name for the
    page*/
    public void getPagesInfo(MangaChapter chapter){
        URL url;
        HttpURLConnection con;

        /* Build the query string using the ids for all the Manga objects received. */
        //https://api.mangadex.org/at-home/server/f3fe6db4-916c-404b-8b26-4eb24981d5e7
        StringBuilder queryString = new StringBuilder("/at-home/server/" + chapter.getId());

        // TODO: Implement looping for total search bigger than limit
        try {
            url = new URL("https", this.apiHostname, this.apiPort, queryString.toString());
            System.out.println(url);
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

        JsonObject dataChapter = json.getMap(Keys.CHAPTER);
        chapter.setHash((String) dataChapter.get("hash"));
        JsonArray data = (JsonArray) dataChapter.get("data");

        for (Object dataItem : data) {
            chapter.addPage((String) dataItem);
        }
    }

    //This is how you get the Cover. See TestCovers.
    public ReadableByteChannel streamCover(MangaCover cover) {
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

    //Getting a page, see TestPage
    public ReadableByteChannel streamPage(MangaChapter chapter, String page) {
        StringBuilder queryString;
        ReadableByteChannel readChannel;
        URL url;

        try {
            //https://api.mangadex.org/at-home/server/f3fe6db4-916c-404b-8b26-4eb24981d5e7
            queryString = new StringBuilder("/data/");
            queryString.append(chapter.getHash());
            queryString.append("/");
            queryString.append(page);
            url = new URL("https", this.dlHostname, this.dlPort, queryString.toString());
            readChannel = Channels.newChannel(url.openStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error reading cover file" + e.getMessage());
        }
        return readChannel;
    }
}
