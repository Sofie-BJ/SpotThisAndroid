package com.example.spotthis.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "image")
public class Image implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "categories")
    private List<String> categories;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "uri")
    private String uri;


    public Image(String description, List<String> categories, String uri) {
        this.description = description;
        this.categories = categories;
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}


