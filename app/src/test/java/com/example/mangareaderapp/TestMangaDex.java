package com.example.mangareaderapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class TestMangaDex {

    @Test
    public void Test01(){
        MangaDex drive = new MangaDex("https://api.mangadex.org/manga?offset=10", "GET");
        JSONObject json = drive.getJson();

        Assert.assertEquals((String)json.get("result"), "ok");

        JSONArray data = (JSONArray) json.get("data");
        Assert.assertTrue("The response should include at least a 10 items (" +
                data.size() + " received)", data.size() >= 10);

        //System.out.println(json);
        Iterator<JSONObject> item = data.iterator();
        while (item.hasNext()) {
            JSONObject itemAttrs = (JSONObject) item.next().get("attributes");
            JSONObject itemTitle = (JSONObject) itemAttrs.get("title");
            String itemTitleEn = (String) itemTitle.get("en");
            System.out.println(itemTitleEn);
        }
    }
}
