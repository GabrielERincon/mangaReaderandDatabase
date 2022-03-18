package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

<<<<<<< HEAD
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<String> mangas = new ArrayList<>();
        mangas.add("a");
        mangas.add("b");
        mangas.add("c");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangas);
        searchDisplay.setAdapter(adapter);

    }

=======
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class SearchActivity {
>>>>>>> ff64a97 (baseline)
}
