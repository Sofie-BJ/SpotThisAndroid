//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license.
//
// Microsoft Cognitive Services (formerly Project Oxford): https://www.microsoft.com/cognitive-services
//
// Microsoft Cognitive Services (formerly Project Oxford) GitHub:
// https://github.com/Microsoft/Cognitive-Vision-Android
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
package com.example.spotthis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotthis.Database.AppDatabase;
import com.example.spotthis.Database.ImageRepository;
import com.example.spotthis.Models.Image;
import com.example.spotthis.helper.ImageHelper;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Tag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeActivity extends AppCompatActivity {

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;

    private ImageView imageView;

    // The edit to show status and result.
    private TextView textView;

    private VisionServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        if (client == null) {
            client = new VisionServiceRestClient(getString(R.string.subscription_key), getString(R.string.subscription_apiroot));
        }

        imageView = findViewById(R.id.selectedImage);
        textView = findViewById(R.id.editTextResult);

        Intent intent = getIntent();
        String uriString = intent.getStringExtra("URI");
        mImageUri = Uri.parse(uriString);
        mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(mImageUri, getContentResolver());
        doAnalyze(uriString);

        if (mBitmap != null) {
            // Show the image on screen.
            //imageView.setImageBitmap(mBitmap);
            imageView.setImageURI(mImageUri);
        }
    }


    public void doAnalyze(String imageuri) {
        textView.setText("Analyzing... \n");

        try {
            new doRequest(imageuri).execute();
        } catch (Exception e) {
            textView.setText("Error encountered. Exception is: " + e.toString());
        }
    }



    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;
        private String imageUri;


        public doRequest(String imageUri) {
            this.imageUri = imageUri;
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                Gson gson = new Gson();
                String[] features = {"ImageType", "Color", "Faces", "Adult", "Categories", "Tags", "Description"};
                String[] details = {};

                // Put the image into an input stream for detection.
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

                AnalysisResult v = client.analyzeImage(inputStream, features, details);

                result = gson.toJson(v);
                Log.d("result", result);


            } catch (Exception e) {
                this.e = e;    // Store error
            }
            if (result.length() > 0) {
                String caption;
                List<String> categories = new ArrayList<>();
                Gson gson = new Gson();
                AnalysisResult analysisResult = gson.fromJson(result, AnalysisResult.class);
                if(analysisResult.description.captions != null){
                    caption = analysisResult.description.captions.get(0).text;
                } else {
                    caption = "No description detected";
                }

                publishProgress(caption);
                for (Tag tag : analysisResult.tags) {
                    if (tag.confidence > 0.90) {
                        publishProgress(tag.name);
                        categories.add(tag.name);
                    }
                }
                Image image = new Image(caption, categories, imageUri);
                Intent intent = new Intent();
                intent.putExtra("IMAGE",image);
                setResult(RESULT_OK,intent);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(!isCancelled()){
                textView.append(values[0] + "\n");
            }
            super.onProgressUpdate(values);
        }
    }
}

