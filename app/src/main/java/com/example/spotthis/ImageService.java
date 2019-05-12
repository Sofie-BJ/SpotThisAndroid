package com.example.spotthis;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class ImageService extends Service {

    private VisionServiceClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new VisionServiceRestClient(getString(R.string.subscription_key),getString(R.string.subscription_apiroot));
    }

    @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();
        String[] features = {"ImageType", "Color", "Faces", "Adult", "Categories"};
        String[] details = {};

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        AnalysisResult v = this.client.analyzeImage(inputStream, features, details);

        String result = gson.toJson(v);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            // Display based on error existence
            String description;
            HashMap<String,Double> categorys = new HashMap<String,Double>();

            if (e != null) {
                this.e = null;
            } else {
                Gson gson = new Gson();
                AnalysisResult result = gson.fromJson(data, AnalysisResult.class);

                /*
                mEditText.append("Image format: " + result.metadata.format + "\n");
                mEditText.append("Image width: " + result.metadata.width + ", height:" + result.metadata.height + "\n");
                mEditText.append("Clip Art Type: " + result.imageType.clipArtType + "\n");
                mEditText.append("Line Drawing Type: " + result.imageType.lineDrawingType + "\n");
                mEditText.append("Is Adult Content:" + result.adult.isAdultContent + "\n");
                mEditText.append("Adult score:" + result.adult.adultScore + "\n");
                mEditText.append("Is Racy Content:" + result.adult.isRacyContent + "\n");
                mEditText.append("Racy score:" + result.adult.racyScore + "\n\n") ;
                */
                
                for (Category category: result.categories) {
                    //mEditText.append("Category: " + category.name + ", score: " + category.score + "\n");
                    categorys.put(category.name,category.score);
                }

                /*
                mEditText.append("\n");
                int faceCount = 0;
                for (Face face: result.faces) {
                    faceCount++;
                    mEditText.append("face " + faceCount + ", gender:" + face.gender + "(score: " + face.genderScore + "), age: " + + face.age + "\n");
                    mEditText.append("    left: " + face.faceRectangle.left +  ",  top: " + face.faceRectangle.top + ", width: " + face.faceRectangle.width + "  height: " + face.faceRectangle.height + "\n" );
                }
                if (faceCount == 0) {
                    mEditText.append("No face is detected");
                }
                mEditText.append("\n");

                mEditText.append("\nDominant Color Foreground :" + result.color.dominantColorForeground + "\n");
                mEditText.append("Dominant Color Background :" + result.color.dominantColorBackground + "\n");

                mEditText.append("\n--- Raw Data ---\n\n");
                mEditText.append(data);
                mEditText.setSelection(0);
                */
            }

            //mButtonSelectImage.setEnabled(true);
        }
    }

}
