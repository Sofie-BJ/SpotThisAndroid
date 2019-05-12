package com.example.spotthis;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ImageDAO {

    @Insert
    void insert(Image image);

    @Query("SELECT * FROM IMAGE")
    List<Image> getImages();

}
