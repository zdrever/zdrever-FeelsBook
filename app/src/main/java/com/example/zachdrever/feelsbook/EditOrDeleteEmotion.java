package com.example.zachdrever.feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditOrDeleteEmotion extends Activity{

    private TextView emotionText;
    private EditText editComment;
    private DatePicker datePicker;
    private Button saveChangesButton;
    private Button deleteEntryButton;
    private FeltEmotion feltEmotion;
    private TimePicker timePicker;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_emotion);

        emotionText = findViewById(R.id.displayEmotionText);
        editComment = findViewById(R.id.editCommentText);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        saveChangesButton = findViewById(R.id.saveChangesButton);
        deleteEntryButton = findViewById(R.id.deleteEmotionButton);

        Intent intent = getIntent();
        feltEmotion = (FeltEmotion) intent.getSerializableExtra(MainActivity.FELTEMOTION);

        emotionText.setText(feltEmotion.getEmotion());
        editComment.setText(feltEmotion.getComment());
        Calendar date = feltEmotion.getDate();
        datePicker.updateDate(date.get(date.YEAR), date.get(date.MONTH), date.get(date.DAY_OF_MONTH));
        timePicker.setHour(date.get(date.HOUR_OF_DAY));
        timePicker.setMinute(date.get(date.MINUTE));
    }


}
