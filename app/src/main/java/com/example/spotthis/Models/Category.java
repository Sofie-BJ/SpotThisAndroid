package com.example.spotthis.Models;

import java.util.List;

public class Category {

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
