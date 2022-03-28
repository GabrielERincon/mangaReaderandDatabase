package com.example.mangareaderapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MangaChapterAdapter extends ArrayAdapter<MangaChapter> {

    ArrayList<MangaChapter> chapters;

    public MangaChapterAdapter(Context context, int textViewResourceId, ArrayList<MangaChapter> chapters) {
        super(context, textViewResourceId, chapters);
        this.chapters = chapters;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        MangaChapter chapter = chapters.get(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_list_view_items2, null);

        TextView textView = (TextView) v.findViewById(R.id.textView);
        //ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        textView.setText("Chapter " + chapter.getChapter());

        return v;

    }

}


