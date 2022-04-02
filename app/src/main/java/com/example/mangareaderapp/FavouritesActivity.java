package com.example.mangareaderapp;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;


public class FavouritesActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private static final String FILE_NAME = "favourites.txt";

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites_layout);

        //Buttons at top of screen
        Button toggleToolbar= (Button) this.findViewById(R.id.toggleButton);
        toggleToolbar.setOnClickListener(this);

        ImageButton homeButton = (ImageButton) this.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        ImageButton toggleInfo = (ImageButton) this.findViewById(R.id.infoButton);
        toggleInfo.setOnClickListener(this);

        ImageButton searchButton = (ImageButton) this.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        //Buttons in the toggleable menu

        Button themes= (Button) findViewById(R.id.themes);
        themes.setOnClickListener(this);

        Button credits= (Button) findViewById(R.id.credits);
        credits.setOnClickListener(this);

        //Search feature elements
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchBar.setIconifiedByDefault(false);

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);

        ArrayList<Manga> mangas = getFavouriteMangas();

        ListView favouritesList = (ListView) this.findViewById(R.id.favouritesList);

        MangaAdapter mangaAdapter = new MangaAdapter(this, R.layout.custom_list_view_items, mangas);
        favouritesList.setAdapter(mangaAdapter);

        favouritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(FavouritesActivity.this, DetailActivity.class);
                i.putExtra("manga", mangas.get(position));
                startActivity(i);
            }
        });
    }

    public ArrayList<Manga> getFavouriteMangas() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FavouritesActivity.this);
        String favouritesSerialized = prefs.getString("favourites", null);
        ArrayList<Manga> mangas = new ArrayList<Manga>();
        System.out.println("Fetching favourite mangas");
        if (favouritesSerialized == null) {
            return mangas;
        }

        @SuppressLint({"NewApi", "LocalSuppress"}) byte[] favouritesData = Base64.getDecoder().decode(favouritesSerialized);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(favouritesData));
            mangas = (ArrayList<Manga>) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing list from preferences: " + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error closing ObjectInputStream from preferences: " + e.getMessage());
            }
        }
        for (Manga manga : mangas) {
            System.out.println("Manga from favorites in Detail: " + manga);
        }
        return mangas;
    }

    @Override
    public void onClick(View v) {

        //Initialized elements to configure visibility
        Group toolbar = (Group) this.findViewById(R.id.group);

        LinearLayout infoMenu = (LinearLayout) this.findViewById(R.id.toggleList);

        SearchView searchBar = (SearchView) this.findViewById(R.id.searchBar);


        //Switch case is used determine what happens when a button is clicked
        switch (v.getId()) {
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

            case R.id.infoButton:
                if (info_visible == false) {
                    infoMenu.setVisibility(View.VISIBLE);
                    info_visible = true;

                } else if (toolbar_visible == true) {
                    infoMenu.setVisibility(View.INVISIBLE);
                    info_visible = false;

                }
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

            case R.id.clear:
                //clear();
                break;

            case R.id.homeButton:
                finish();
                startActivity(new Intent(FavouritesActivity.this, MainActivity.class));
                break;

            case R.id.themes:
                finish();
                startActivity(new Intent(FavouritesActivity.this, ThemeActivity.class));
                break;

            case R.id.credits:
                finish();
                startActivity(new Intent(FavouritesActivity.this, CreditsActivity.class));
                break;

            default:
                break;

        }

    }

}