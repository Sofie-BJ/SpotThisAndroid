package com.example.spotthis;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.spotthis.Database.AppDatabase;
import com.example.spotthis.Database.DatabaseUtilityHelper;
import com.example.spotthis.Models.Category;
import com.example.spotthis.Models.Image;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ImageActivity extends AppCompatActivity {

    // Flag to indicate which task is to be performed.

    private List<Category> categories;
    private AppDatabase database;
    private DatabaseUtilityHelper databaseUtilityHelper = new DatabaseUtilityHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageactivity);
        database = AppDatabase.getAppDatabase(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        try {
            CategoryViewAdapter adapter = new CategoryViewAdapter(new CategoryAsyncTask().execute().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private class CategoryAsyncTask extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... voids) {
            List<Image> images = database.imageDAO().getImages();
            categories = databaseUtilityHelper.getCategories(images);

            return categories;
        }
    }


}
