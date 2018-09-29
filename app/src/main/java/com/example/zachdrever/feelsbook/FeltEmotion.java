package com.example.zachdrever.feelsbook;

import java.util.Date;

public class FeltEmotion {

    private Emotion emotion;
    private Date date;
    private String comment;

    FeltEmotion(Emotion emotion, String comment, Date date){
        this.emotion = emotion;
        this.date = date;
        this.comment = comment;
    }

    public String getEmotion() {
        return emotion.getEmotion();
    }

    public String getDate(){
        return this.date.toString();
    }
    public void setDate(long date){this.date.setTime(date); }

    public String getComment(){
        return this.comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
}