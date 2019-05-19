package com.example.spotthis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.spotthis.Models.Image;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Image> images;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_category_images);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        images = (ArrayList<Image>) bundle.getSerializable("IMAGESCATEGORY");

        ImagesCategoryViewAdapter adapter = new ImagesCategoryViewAdapter(images, this);
        recyclerView.setAdapter(adapter);



    }



}
