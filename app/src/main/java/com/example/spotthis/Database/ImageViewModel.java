package com.example.spotthis.Database;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.spotthis.Models.Image;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<List<Image>> images;


    public ImageViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new ImageRepository(application);
        images = imageRepository.getImages();
    }

    public void insert(Image image) {
        imageRepository.insert(image);
    }

    public LiveData<List<Image>> getImages() {
        return images;
    }

}
