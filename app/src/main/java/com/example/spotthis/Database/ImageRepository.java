package com.example.spotthis.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.spotthis.Models.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository {

    LiveData<List<Image>> imagesFromDB;
    private final ImageDAO imageDAO;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();

    public ImageRepository(Application application) {
        AppDatabase db = AppDatabase.getAppDatabase(application);
        imageDAO = db.imageDAO();
        imagesFromDB = imageDAO.getImages();
    }

    public LiveData<List<Image>> getImages() {
        return imagesFromDB;
    }

    public void insert(Image image) {
        insertAsync(image);
    }

    private void insertAsync(final Image image) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    imageDAO.insert(image);
                } catch (Exception e) {
                }
            }
        }).start();
    }
}
