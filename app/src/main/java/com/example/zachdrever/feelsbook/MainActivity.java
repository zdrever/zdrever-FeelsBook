package com.example.zachdrever.feelsbook;
//TODO: https://stackoverflow.com/questions/19765938/show-and-hide-a-view-with-a-slide-up-down-animation

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity{

    private static final String FILENAME = "feelsbook.wav";
    public static final String FELTEMOTION = "com.example.zachdrever.feelsbook.FELTEMOTION";

    // emotionList elements
    private ListView emotionHistory;
    private FloatingActionButton addEmotionButton;
    private ArrayList<FeltEmotion> feltEmotionList;
    private ArrayAdapter<FeltEmotion> emotionListAdapter;

    // addEmotionView elements
    private View addEmotionView;
    private Button saveEmotionButton;
    private Button cancelButton;
    private EditText commentText;
    private Spinner emotionSpinner;
    private ArrayAdapter<Emotion> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // FeltEmotion List View setup
        addEmotionView = findViewById(R.id.addEmotionLayout);
        emotionHistory = findViewById(R.id.emotionListView);
        addEmotionButton = findViewById(R.id.addEmotionButton);
        loadFromFile();
        emotionListAdapter = new ArrayAdapter<FeltEmotion>(this, R.layout.list_item, feltEmotionList);
        emotionHistory.setAdapter(emotionListAdapter);

        saveEmotionButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        commentText = findViewById(R.id.addCommentEditText);
        emotionSpinner = findViewById(R.id.emotionSelectionSpinner);
        spinnerAdapter = new ArrayAdapter<Emotion>(this, android.R.layout.simple_spinner_dropdown_item, Emotion.values());
        emotionSpinner.setAdapter(spinnerAdapter);

        emotionHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                           int position, long id) {

                Intent intent = new Intent(adapterView.getContext(), EditOrDeleteEmotion.class);
                intent.putExtra(FELTEMOTION, feltEmotionList.get(position));
                startActivity(intent);
            }
        });
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FeltEmotion>>(){}.getType();

            feltEmotionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            feltEmotionList = new ArrayList<FeltEmotion>();
        }
    }

    private void saveInFile() {
        try {
            Emotion e = (Emotion) emotionSpinner.getSelectedItem();
            String c = commentText.getText().toString(); //TODO: throw error for comment > chars
            Calendar d = Calendar.getInstance();
            feltEmotionList.add(new FeltEmotion(e, c, d));

            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            gson.toJson(feltEmotionList, out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void slideUp(View view){
        view.setVisibility(view.INVISIBLE);
        view.setEnabled(false);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from above itself to its current position
    public void slideDown(View view){
        view.setVisibility(View.VISIBLE);
        view.setEnabled(true);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void addEmotionButtonClick(View view) {
        commentText.setText("");
        emotionHistory.setEnabled(false);
        addEmotionButton.hide();
        addEmotionButton.setEnabled(false);
        slideDown(addEmotionView);
    }

    public void saveEmotionButtonClick(View view){
        saveInFile();
        emotionListAdapter.notifyDataSetChanged();
        closeAddEmotionView(view);
    }

    public void closeAddEmotionView(View view){
        emotionHistory.setEnabled(true);
        slideUp(addEmotionView);
        addEmotionButton.show();
        addEmotionButton.setEnabled(true);
    }
}
