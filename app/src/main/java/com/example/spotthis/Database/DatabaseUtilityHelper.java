package com.example.spotthis.Database;

import com.example.spotthis.Models.Category;
import com.example.spotthis.Models.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseUtilityHelper {

    public List<Category> getCategories(List<Image> images) {

        HashMap<String, List<Image>> dict = new HashMap<>();

        for (Image image : images) {
            List<String> categories = image.getCategories();
            for (String category : categories) {
                if (!dict.containsKey(category)) {
                    ArrayList<Image> imagesInCategory = new ArrayList<>();
                    imagesInCategory.add(image);
                    dict.put(category, imagesInCategory);
                } else {
                    dict.get(category).add(image);
                }
            }

        }

        ArrayList<Category> categories = new ArrayList<>();
        for (String category: dict.keySet()) {
            categories.add(new Category(category, dict.get(category)));
        }

        return categories;
    }


}
