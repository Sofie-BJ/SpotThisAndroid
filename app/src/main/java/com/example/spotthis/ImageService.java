package com.example.spotthis;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Description;
import com.microsoft.projectoxford.vision.contract.Tag;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ImageService{

    private VisionServiceClient client;
    private Image image;

    public ImageService(){
        client = new VisionServiceRestClient("b7bf67af99e24f72a9cd804c4ec10140","https://westeurope.api.cognitive.microsoft.com/vision/v1.0");
    }

    public void analyzeImage(Bitmap imageBitmap){

        try {
            new doRequest(imageBitmap).execute();
        } catch (Exception e)
        {
        }
    }

    private String process(Bitmap bitmap) throws VisionServiceException, IOException {
        Gson gson = new Gson();
        String[] features = {"ImageType", "Color", "Faces", "Adult", "Categories", "Tags", "Description"};
        String[] details = {};

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        AnalysisResult v = this.client.analyzeImage(inputStream, features, details);

        String result = gson.toJson(v);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;
        private Bitmap imageBitmap;

        public doRequest(Bitmap imageBitmap) {
            this.imageBitmap = imageBitmap;
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process(imageBitmap);
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            // Display based on error existence
            String caption;
            HashMap<String,Double> tags = new HashMap<String,Double>();

            if (e != null) {
                this.e = null;
            } else {
                Gson gson = new Gson();
                AnalysisResult result = gson.fromJson(data, AnalysisResult.class);

                result.description.tags.get(0);

                for (Tag tag: result.tags) {
                    tags.put(tag.name,tag.confidence);
                }

            }

        }
    }

}
