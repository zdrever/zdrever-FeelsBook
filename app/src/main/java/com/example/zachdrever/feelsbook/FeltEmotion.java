package com.example.zachdrever.feelsbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FeltEmotion implements Serializable {

    private Emotion emotion;
    private Calendar date;
    private String comment;

    FeltEmotion(Emotion emotion, String comment, Calendar date){
        this.emotion = emotion;
        this.date = date;
        this.comment = comment;
    }

    public Emotion getEmotion() { return this.emotion; }

    public Calendar getDate(){
        return this.date;
    }
    public void setDate(Calendar date){
        this.date = date;
    }

    public String getComment(){
        return this.comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy @ hh:mm");
        return this.emotion.toString() + "\n" + sdf.format(this.date.getTime());
    }
}