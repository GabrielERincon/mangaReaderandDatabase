package com.example.mangareaderapp;

public class Item {

    String mangaName;
    int mangaImage;

    public Item(String mangaName,int mangaImage) {
        this.mangaImage = mangaImage;
        this.mangaName = mangaName;
    }

    public String getMangaName() {
        return mangaName;
    }

    public int getMangaImage() {
        return mangaImage;
    }

}
