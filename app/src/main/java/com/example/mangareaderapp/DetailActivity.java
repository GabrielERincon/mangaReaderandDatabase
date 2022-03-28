package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_detail);

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
        MangaDex mangadex = new MangaDex();

        Manga manga = (Manga) intent.getSerializableExtra("manga");

        TextView mangaNameBox = (TextView) findViewById(R.id.mangaName);
        mangaNameBox.setText(manga.getTitle());

        ListView chapterList = (ListView) findViewById(R.id.chapterList);

        mangadex.getChapterInfo(manga);
        ArrayList<MangaChapter> chapters = (ArrayList<MangaChapter>) manga.getChapters();

        MangaChapterAdapter adapter = new MangaChapterAdapter(this,
                android.R.layout.simple_list_item_1, chapters);
        chapterList.setAdapter(adapter);

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MangaChapter chapter = adapter.getItem(position);
                Intent i = new Intent(DetailActivity.this, ChapterActivity.class);
                i.putExtra("chapter", chapter);
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
