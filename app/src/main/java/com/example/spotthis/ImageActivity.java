package com.example.spotthis;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ImageActivity extends AppCompatActivity {

    // Flag to indicate which task is to be performed.


    private List<Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageactivity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                images = AppDatabase.getAppDatabase(getApplicationContext()).imageDAO().getImages();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CategoryViewAdapter adapter = new CategoryViewAdapter(images);

    }


}
