package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements Serializable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        /*ListView searchDisplay = (ListView) findViewById(R.id.searchList);
        ArrayList<String> mangas = new ArrayList<>();
        mangas.add("a");
        mangas.add("b");
        mangas.add("c");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangas);
        searchDisplay.setAdapter(adapter);*/

        handleIntent(getIntent());

    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String x) {
        ListView searchDisplay = (ListView) findViewById(R.id.searchList);

        //Searches for the required manga in the database from MangaDex class
        MangaDex mangadex = new MangaDex();
        ArrayList<Manga> mangas = (ArrayList<Manga>) mangadex.searchManga(x);
        mangadex.getCoverInfo(mangas);
        for (Manga manga : mangas) {
            // Just load the first available cover for now.
            MangaCover cover = manga.getCovers().get(0);
            // We don't do anything with the resulting bytes here, but the cover object
            // will have them ready so that MangaAdapter can use it as the results list
            // is populated.
            mangadex.getCoverBytes(cover, 256);
        }

        MangaAdapter mangaAdapter = new MangaAdapter(this, R.layout.custom_list_view_items, mangas);
        searchDisplay.setAdapter(mangaAdapter);

        /*ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangas);
        searchDisplay.setAdapter(adapter);*/

        searchDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String mangaName = adapter.getItem(position).toString();
                //String mangaName = mangaAdapter.getItem(position).toString();
                Intent i = new Intent(SearchActivity.this, ChapterSelection.class);
                i.putExtra("manga", mangas.get(position));
                //i.putExtra("key", mangaName);
                startActivity(i);

            }
        });

    }

}

