package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

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
        ArrayList<Manga> mangaPull = new ArrayList<>();
        MangaDex mangadex = new MangaDex();
        mangaPull = (ArrayList<Manga>) mangadex.search_manga(x);

        //Actual list used for display
        ArrayList<String> mangas = new ArrayList<>();

        /*for (Manga manga : mangaPull) {
            mangas.add(manga.getTitle());
        }*/

        mangas.add("a");
        mangas.add("b");
        mangas.add("c");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangas);
        searchDisplay.setAdapter(adapter);

        searchDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(SearchActivity.this,
                        "Hello" + adapter.getItem(position),
                        Toast.LENGTH_SHORT).show();*/
                String mangaName = adapter.getItem(position).toString();
                Intent i = new Intent(SearchActivity.this, ChapterSelection.class);
                i.putExtra("key", mangaName);
                startActivity(i);

            }
        });

    }

}

