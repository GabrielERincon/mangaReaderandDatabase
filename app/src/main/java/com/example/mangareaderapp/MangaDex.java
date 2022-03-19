package com.example.mangareaderapp;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MangaDex {
    private URL url;
    private int responseCode = -1;
    private HttpURLConnection con;
    private String raw = "";
    private JsonObject data;

    public MangaDex(String url, String requestMethod) {
        try {
            String line;
            this.url = new URL(url);
            con = (HttpURLConnection) this.url.openConnection();
            con.setRequestMethod(requestMethod);
            con.connect();

            if (con.getResponseCode() != 200) {
                throw new RuntimeException("Response code: " + con.getResponseCode());
            } else {

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.data = (JsonObject) Jsoner.deserialize(reader);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getRawString() {
        return this.raw;
    }

    public JsonObject getJson() {
        return this.data;
    }
}
