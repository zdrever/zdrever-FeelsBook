package com.example.zachdrever.feelsbook;
//TODO: https://stackoverflow.com/questions/19765938/show-and-hide-a-view-with-a-slide-up-down-animation

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity{

    private static final String FILENAME = "feelsbook.wav";
    private ListView emotionHistory;
    private View addEmotionView;
    private FloatingActionButton addEmotionButton;
    private Boolean viewVisible = Boolean.FALSE;

    ArrayList<Emotion> emotionList;
    ArrayAdapter<Emotion> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        addEmotionView = findViewById(R.id.addEmotionView);
        emotionHistory = findViewById(R.id.emotionListView);
        addEmotionButton = findViewById(R.id.addEmotionButton);

        loadFromFile();

        adapter = new ArrayAdapter<Emotion>(this, R.layout.main, emotionList);

        emotionHistory.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();

            emotionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            emotionList = new ArrayList<Emotion>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            gson.toJson(emotionList, out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void onSlideViewButtonClick(View view) {
        if (viewVisible) {
            slideDown(addEmotionView);
        } else {
            slideUp(addEmotionView);
        }
        viewVisible = !viewVisible;
    }
}
