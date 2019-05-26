package com.example.spotthis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.spotthis.Database.AppDatabase;
import com.example.spotthis.Models.Image;
import com.example.spotthis.helper.ImageHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_IMAGE = 0;
    private AppDatabase database;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getAppDatabase(this);
        imageView = (ImageView) findViewById(R.id.imageViewDb);
    }

    public void categoryView(View view) {
        Intent intent;
        intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void selectImage(View view){
        Intent intent;
        intent = new Intent(this, com.example.spotthis.AnalyzeActivity.class);
        startActivity(intent);
    }

    public void ImageFromDB(View view ) throws ExecutionException, InterruptedException {
        imageView.setImageBitmap(new ImageAsyncTask().execute().get());
    }

    private class ImageAsyncTask extends AsyncTask<Void,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            List<Image> images = database.imageDAO().getImages();
            Image image = images.get(0);

            Uri imageUri = Uri.parse(image.getUri());


            Bitmap mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                    imageUri, getContentResolver());
            if (mBitmap != null) {
                return mBitmap;
            }
            return null;
        }


    }



}
