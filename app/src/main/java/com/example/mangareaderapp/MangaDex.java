package com.example.mangareaderapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MangaDex {
    private URL url;
    private int responseCode = -1;
    private HttpURLConnection con;
    private String raw = "";
    private JSONObject data;
    private JSONParser parser = new JSONParser();

    public MangaDex(String url, String requestMethod){
        try{
            String line;
            this.url = new URL(url);
            con = (HttpURLConnection) this.url.openConnection();
            con.setRequestMethod(requestMethod);
            con.connect();

            if(con.getResponseCode() != 200){
                throw new RuntimeException("Response code: " + con.getResponseCode());
            } else {

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder rawbuilder = new StringBuilder();
                String ls = System.getProperty("line.separator");

                line = reader.readLine();
                rawbuilder.append(line);
                line = reader.readLine();

                while (line != null) {
                    rawbuilder.append(ls);
                    rawbuilder.append(line);
                    line = reader.readLine();
                }

                reader.close();

                this.raw = rawbuilder.toString();

                // Convert the raw data to json object
                this.parser = new JSONParser();
                this.data = (JSONObject) this.parser.parse(this.raw);

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getRawString(){
        return this.raw;
    }

    public JSONObject getJson(){
        return this.data;
    }
}
