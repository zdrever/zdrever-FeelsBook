package com.example.zachdrever.feelsbook;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * FeltEmotion implements Comparable
 *
 * Represents an emotion that a person has felt for the feelsbook
 * application.
 */
public class FeltEmotion implements Comparable<FeltEmotion> {

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
    public void setDate(Calendar date){ this.date = date; }

    public String getComment(){
        return this.comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy @ hh:mm");
        String format;
        if (this.comment.equals("")) {
            format = this.emotion.toString() + "\n" + sdf.format(this.date.getTime());
        } else {
            format = this.emotion.toString() + "\n" + this.comment + "\n" + sdf.format(this.date.getTime());
        }
        return format;
    }

    public int compareTo(FeltEmotion emotion) {
        return Long.compare(emotion.getDate().getTimeInMillis(), this.date.getTimeInMillis());
    }
}