package com.example.zachdrever.feelsbook;

import android.content.Context;
import android.net.sip.SipAudioCall;

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
import java.util.Collection;

public class FeltEmotionList {

    protected ArrayList<FeltEmotion> emotionList;

    FeltEmotionList(){
        this.emotionList = new ArrayList<FeltEmotion>();
    }

    FeltEmotionList(ArrayList<FeltEmotion> emotionList){
        this.emotionList = emotionList;
    }

    protected void clear(){
        this.emotionList.clear();
    }

    protected void addAll(Collection<FeltEmotion> emotions){
        this.emotionList.addAll(emotions);
    }

    protected void addFeltEmotion(FeltEmotion emotion){
        this.emotionList.add(0, emotion);
    }

    protected void removeFeltEmotion(FeltEmotion emotion){
        this.emotionList.remove(emotion);
    }

    protected ArrayList<FeltEmotion> getEmotionList(){
        return this.emotionList;
    }

    protected void editFeltEmotion(int position, String comment, Calendar date){
        FeltEmotion emotion = this.getFeltEmotion(position);
        emotion.setComment(comment);
        emotion.setDate(date);
    }

    protected FeltEmotion getFeltEmotion(int position){
        return this.emotionList.get(position);
    }
}
