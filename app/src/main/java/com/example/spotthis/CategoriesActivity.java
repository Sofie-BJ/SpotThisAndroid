package com.example.spotthis;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.spotthis.Database.AppDatabase;
import com.example.spotthis.Database.DatabaseUtilityHelper;
import com.example.spotthis.Database.ImageViewModel;
import com.example.spotthis.Models.Category;
import com.example.spotthis.Models.Image;
import com.example.spotthis.helper.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoriesActivity extends AppCompatActivity {

    // Flag to indicate which task is to be performed.

    private RecyclerView recyclerView;
    private DatabaseUtilityHelper databaseUtilityHelper = new DatabaseUtilityHelper();

    // File of the photo taken with camera
    private File mFilePhotoTaken;


    // The URI of photo taken from gallery
    private Uri mUriPhotoTaken;

    // Flag to indicate the request of the next task to be performed
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    private static final int ANALYZED_PICTURE = 2;

    private ImageViewModel imageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        final CategoryViewAdapter adapter = new CategoryViewAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);

        imageViewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                List<Category> categories = databaseUtilityHelper.getCategories(images);
                adapter.setCategories(categories);
                Toast.makeText(getApplicationContext(), "Image successfully added!",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.actions);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                }
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                // When the button of "Take a Photo with Camera" is pressed.
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Save the photo taken to a temporary file.
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    try {
                        mFilePhotoTaken = File.createTempFile(
                                "IMG_",  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* directory */
                        );

                        // Create the File where the photo should go
                        // Continue only if the File was successfully created
                        if (mFilePhotoTaken != null) {
                            mUriPhotoTaken = FileProvider.getUriForFile(this,
                                    "com.example.spotthis.fileprovider",
                                    mFilePhotoTaken);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);

                            // Finally start camera activity
                            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                        }
                    } catch (IOException e) {
                        setInfo(e.getMessage());
                    }
                }
                break;


            case R.id.upload:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("mFilePhotoTaken/*");
                if (intent1.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent1, REQUEST_SELECT_IMAGE_IN_ALBUM);
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    // Set the information panel on screen.
    private void setInfo(String info) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(info);
    }

    // Called when image selection is done.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AnalyzeActivity", "onActivityResult");

        switch (requestCode) {
            case ANALYZED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Image image = (Image)data.getSerializableExtra("IMAGE");
                    imageViewModel.insert(image);
                    break;
                }

            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this.getApplicationContext(), AnalyzeActivity.class);
                    if (mUriPhotoTaken != null) {
                        intent.putExtra("URI", mUriPhotoTaken.toString());
                    }
                    startActivityForResult(intent,ANALYZED_PICTURE);
                    break;

                }

            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this.getApplicationContext(), AnalyzeActivity.class);
                    if (data.getData() != null) {
                        intent.putExtra("URI", data.getData().toString());
                    }
                    startActivityForResult(intent,ANALYZED_PICTURE);
                    break;
                }
        }



    }

}



