package com.example.zachdrever.feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * EditOrDeleteEmotion
 *
 * App activity for editing and deleting emotions
 */
public class EditOrDeleteEmotion extends AppCompatActivity {

    private TextView emotionText;
    private EditText editComment;
    private DatePicker datePicker;
    private Button saveChangesButton;
    private Button deleteEntryButton;
    private FeltEmotion feltEmotion;
    private Integer position;
    private TimePicker timePicker;
    private FeltEmotionListController emotionListController;

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

        emotionListController = new FeltEmotionListController(this);

        Intent intent = getIntent();
        position = (Integer) intent.getSerializableExtra(MainActivity.FELTEMOTION);

        feltEmotion = emotionListController.getFeltEmotion(position);
        emotionText.setText(feltEmotion.getEmotion().toString());
        editComment.setText(feltEmotion.getComment());
        Calendar calendar = feltEmotion.getDate();
        datePicker.setMaxDate(new Date().getTime());
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));
    }

    public void editEmotion(View v){
        String c = editComment.getText().toString();
        Calendar d = Calendar.getInstance();
        d.set(
            datePicker.getYear(),
            datePicker.getMonth(),
            datePicker.getDayOfMonth(),
            timePicker.getHour(),
            timePicker.getMinute()
        );

        emotionListController.editFeltEmotion(position, c, d);
        finish();
    }

    public void deleteEmotion(View v){
        emotionListController.removeFeltEmotion(feltEmotion);
        this.finish();
    }


}
