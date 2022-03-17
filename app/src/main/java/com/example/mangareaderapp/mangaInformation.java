package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class mangaInformation extends AppCompatActivity implements View.OnClickListener {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_information);

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