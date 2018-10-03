package com.example.zachdrever.feelsbook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class FeltEmotionList {

    private ArrayList<FeltEmotion> emotionList;

    FeltEmotionList(){
        this.emotionList = new ArrayList<>();
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
        this.sort();
    }

    protected FeltEmotion getFeltEmotion(int position){
        return this.emotionList.get(position);
    }

    private void sort(){
        Collections.sort(this.emotionList);
    }

    protected HashMap<String, Integer> countEmotions(){
        HashMap<String, Integer> counts = new HashMap<>();
        for (Emotion e: Emotion.values()){
            counts.put(e.toString(), 0);
        }

        for (FeltEmotion e: emotionList) {
            counts.put(e.getEmotion().toString(), counts.get(e.getEmotion().toString()) + 1);
        }

        return counts;
    }

    protected Integer countEmotion(String emotion){
        int i = 0;
        for (FeltEmotion e : this.emotionList){
            i += e.getEmotion().toString().equals(emotion) ? 1 : 0;
        }
        return i;
    }
}
