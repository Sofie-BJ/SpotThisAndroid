package com.example.spotthis.Database;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.spotthis.Models.Image;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<Integer> inserResult;


    public ImageViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new ImageRepository(application);
        inserResult = imageRepository.getInsertResult();
    }

    public void insert(Image image) {
        imageRepository.insert(image);
    }

    public List<Image> getImages() {
        return imageRepository.getImages();
    }

    public LiveData<Integer> getInserResult() {
        return inserResult;
    }


}
