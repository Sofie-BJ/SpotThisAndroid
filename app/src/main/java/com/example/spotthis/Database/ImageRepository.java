package com.example.spotthis.Database;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.spotthis.Models.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageRepository {

    List<Image> imagesFromDB = new ArrayList<>();
    private final ImageDAO imageDAO;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();

    private static ImageRepository IMAGEREP;

    private static ImageRepository getImageRepository() {
        if (IMAGEREP == null) {
            IMAGEREP = new ImageRepository();
        }
    }


    public ImageRepository(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        imageDAO = db.imageDAO();
    }

    public List<Image> getImages() {
        return getImagesAsync();
    }

    private List<Image> getImagesAsync() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    imagesFromDB = imageDAO.getImages();
                } catch (Exception e) {
                    System.out.println("Couldn't retrieve images");
                }
            }
        }).start();
        return imagesFromDB;
    }


    public void insert(Image image) {
        insertAsync(image);
    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    private void insertAsync(final Image image) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    imageDAO.insert(image);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }

            }
        }).start();

    }
}
