package com.example.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity implements View.OnClickListener {

    boolean searchClicked = false;
    boolean toolbar_visible = true;
    boolean info_visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_reading);

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

        //Down below, ive already added onclick listeners for the left and right arrow buttons
        //This imageview is used to display the selected page from chapter
        ImageView mangaPageHolder = (ImageView) this.findViewById(R.id.mangaPageDisplay);
        mangaPageHolder.setVisibility(View.VISIBLE);


        //This is the id for the textview that is between the buttons, can be used to display page number
        TextView pageNumber = (TextView) this.findViewById(R.id.pageNumber);

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            TextView mangaNameBox = (TextView) findViewById(R.id.mangaName);
            mangaNameBox.setText(value);

        }*/

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

        ImageButton leftArrow = (ImageButton) this.findViewById(R.id.leftArrow);
        leftArrow.setOnClickListener(this);

        ImageButton rightArrow = (ImageButton) this.findViewById(R.id.rightArrow);
        rightArrow.setOnClickListener(this);

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
            case R.id.leftArrow:


                break;
            case R.id.rightArrow:


                break;
            default:
                break;

        }

    }

}
