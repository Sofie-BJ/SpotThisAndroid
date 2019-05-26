package com.example.spotthis;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotthis.Models.Image;

import java.io.Serializable;

public class ImageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        ImageView imageDetail = findViewById(R.id.imageDetail);
        TextView description = findViewById(R.id.description);
        TextView tags = findViewById(R.id.tags);

        Intent intent = getIntent();
        Image image = (Image) intent.getSerializableExtra("IMAGE");

        imageDetail.setImageURI(Uri.parse(image.getUri()));
        description.setText(image.getDescription());


        StringBuilder tagsBuilder = new StringBuilder();
        for (String category : image.getCategories()) {
            tagsBuilder.append(category + " \n");
        }
        tags.setText(tagsBuilder);


    }
}
