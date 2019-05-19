package com.example.spotthis.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.spotthis.Models.Image;

import java.util.List;

@Dao
public interface ImageDAO {

    @Insert
    void insert(Image image);

    @Query("SELECT * FROM IMAGE")
    List<Image> getImages();

}
