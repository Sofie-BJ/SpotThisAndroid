package com.example.spotthis.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    private String categoryname;
    private List<Image> categoryimages;

    public Category(String categoryname, List<Image> categoryimages) {
        this.categoryname = categoryname;
        this.categoryimages = categoryimages;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public List<Image> getCategoryimages() {
        return categoryimages;
    }


}
