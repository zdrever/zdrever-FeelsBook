package com.example.zachdrever.feelsbook;

import android.content.Context;

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
import java.util.HashMap;


public class FeltEmotionListController {

    private final String FILENAME = "emotionlist.wav";

    private Context context;
    private FeltEmotionList emotionList;

    FeltEmotionListController(Context context){
        this.context = context;
        this.emotionList = new FeltEmotionList();
        loadFromFile();
    }

    protected void loadFromFile() {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FeltEmotion>>(){}.getType();

            this.emotionList.clear();
            ArrayList<FeltEmotion> emotions = gson.fromJson(in, listType);
            this.emotionList.addAll(emotions);

        } catch (FileNotFoundException e) {
            emotionList = new FeltEmotionList();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            gson.toJson(emotionList.getEmotionList(), out);
            out.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFeltEmotion(FeltEmotion emotion){
        emotionList.addFeltEmotion(emotion);
        saveInFile();
    }

    public void removeFeltEmotion(FeltEmotion emotion) {
        emotionList.removeFeltEmotion(emotion);
        saveInFile();
    }

    public ArrayList<FeltEmotion> getFeltEmotionArrayList(){
        return emotionList.getEmotionList();
    }

    public FeltEmotion getFeltEmotion(int position){
        return this.emotionList.getFeltEmotion(position);
    }

    public void editFeltEmotion(int position, String comment, Calendar date){
        emotionList.editFeltEmotion(position, comment, date);
        saveInFile();
    }

    public HashMap<String,String> getCounts(){
        HashMap<String, Integer> counts = emotionList.countEmotions();
        HashMap<String, String> countStrings = new HashMap<>();
        for (String k: counts.keySet()){
            countStrings.put(k, k + " : " + counts.get(k).toString());
        }
        return countStrings;
    }

    public String getCount(String emotion) {
        return emotion + " : " + emotionList.countEmotion(emotion);
    }
}
