package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterSelection extends AppCompatActivity implements View.OnClickListener, Serializable {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_selection);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);

        Button mangaInfoButton = (Button) findViewById(R.id.mangaInfo);
        mangaInfoButton.setOnClickListener(this);

        ImageButton searchButton = (ImageButton) this.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        ImageButton toggleInfo = (ImageButton) this.findViewById(R.id.infoButton);
        toggleInfo.setOnClickListener(this);

        Button toggleToolbar = (Button) this.findViewById(R.id.toggleButton);
        toggleToolbar.setOnClickListener(this);

        Group toolbar = (Group) findViewById(R.id.group);

        LinearLayout infoMenu = (LinearLayout) findViewById(R.id.toggleList);


        chapterSelection();

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("manga");
            TextView mangaNameBox = (TextView) findViewById(R.id.mangaName);
            mangaNameBox.setText(value);

        }*/

    }

    private void chapterSelection() {

        Intent intent = getIntent();
            Manga selectedManga = (Manga) intent.getSerializableExtra("manga");

            String value = selectedManga.getTitle();
            TextView mangaNameBox = (TextView) findViewById(R.id.mangaName);
            mangaNameBox.setText(value);


        ListView chapters = (ListView) findViewById(R.id.chapters);
        ArrayList<String> availableChapters = new ArrayList<>();
        availableChapters.add("Chapter 1");
        availableChapters.add("Chapter 2");
        availableChapters.add("Chapter 3");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, availableChapters);
        chapters.setAdapter(adapter);

        chapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String mangaName = adapter.getItem(position).toString();
                Intent i = new Intent(ChapterSelection.this, ChapterReading.class);
                //i.putExtra("key", mangaName);
                startActivity(i);

            }
        });

    }

    @Override
    public void onClick(View v) {
        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);

        Button mangaInfoButton = (Button) findViewById(R.id.mangaInfo);
        mangaInfoButton.setOnClickListener(this);

        ImageButton homeButton = (ImageButton) this.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        ImageButton searchButton = (ImageButton) this.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        ImageButton toggleInfo = (ImageButton) this.findViewById(R.id.infoButton);
        toggleInfo.setOnClickListener(this);

        Button toggleToolbar = (Button) this.findViewById(R.id.toggleButton);
        toggleToolbar.setOnClickListener(this);

        Group toolbar = (Group) findViewById(R.id.group);

        LinearLayout infoMenu = (LinearLayout) findViewById(R.id.toggleList);

        switch (v.getId()) {
            case R.id.homeButton:
                finish();
                startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.searchButton:
                if (searchClicked == false) {
                    searchBar.setVisibility(View.INVISIBLE);
                    searchClicked = true;

                } else if (searchClicked == true) {
                    searchBar.setVisibility(View.VISIBLE);
                    searchClicked = false;

                }

                break;
            case R.id.infoButton:
                if (info_visible == false) {
                    infoMenu.setVisibility(View.VISIBLE);
                    info_visible = true;

                } else if (toolbar_visible == true) {
                    infoMenu.setVisibility(View.INVISIBLE);
                    info_visible = false;

                }

                break;
            case R.id.toggleButton:
                if (toolbar_visible == false) {
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar_visible = true;

                } else if (toolbar_visible == true) {
                    toolbar.setVisibility(View.INVISIBLE);
                    toolbar_visible = false;
                    infoMenu.setVisibility(View.INVISIBLE);
                    info_visible = false;

                }

                break;
            default:
                break;

        }

    }

}
