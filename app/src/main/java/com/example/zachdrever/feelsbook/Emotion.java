package com.example.zachdrever.feelsbook;

public enum Emotion {
    LOVE ("Love"),
    JOY ("Joy"),
    SURPRISE ("Surprise"),
    ANGER ("Anger"),
    SADNESS ("Sadness"),
    FEAR ("Fear");

    private String emotion;

    Emotion(String emotion){
        this.emotion = emotion;
    }

    public String getEmotion(){ return this.emotion; }
    public void setEmotion(String emotion) { this.emotion = emotion;}
    public static String getEmotionAtIndex(int i){ return Emotion.values()[i].toString();}
}
