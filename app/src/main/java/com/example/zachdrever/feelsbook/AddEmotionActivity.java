package com.example.zachdrever.feelsbook;


import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class AddEmotionActivity extends Activity{

    Button saveButton;
    Button cancelButton;
    EditText commentText;
    Spinner emotionSpinner;
    ArrayAdapter<String> spinnerAdapter;
    Resources res = getResources();

    AddEmotionActivity(){
        this.saveButton = (Button) findViewById(R.id.saveButton);
        this.cancelButton = (Button) findViewById(R.id.cancelButton);
        this.commentText = (EditText) findViewById(R.id.addCommentEditText);
        this.emotionSpinner = (Spinner) findViewById(R.id.emotionSelectionSpinner);
        String[] spinnerItems = res.getStringArray(R.array.emotions_array);
        this.spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, spinnerItems
        );
        emotionSpinner.setAdapter(this.spinnerAdapter);
    }



}
