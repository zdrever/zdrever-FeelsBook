package com.example.zachdrever.feelsbook;
//TODO: https://stackoverflow.com/questions/19765938/show-and-hide-a-view-with-a-slide-up-down-animation

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;

/**
 * MainActivity
 *
 * Implements functionality such as seeing the emotion history,
 * adding emotions, selecting emotions to edit or delete, and
 * displaying counts of all the emotions.
 */
public class MainActivity extends AppCompatActivity{

    public static final String FELTEMOTION = "com.example.zachdrever.feelsbook.FELTEMOTION";

    // emotionList elements
    private ListView emotionHistory;
    private FloatingActionButton addEmotionButton;
    private FeltEmotionListController emotionListController;
    private ArrayAdapter<FeltEmotion> emotionListAdapter;

    // addEmotionView elements
    private View addEmotionView;
    private Button saveEmotionButton;
    private Button cancelButton;
    private EditText commentText;
    private Spinner emotionSpinner;
    private ArrayAdapter<Emotion> spinnerAdapter;

    private TextView loveCount;
    private TextView joyCount;
    private TextView surpriseCount;
    private TextView angerCount;
    private TextView sadnessCount;
    private TextView fearCount;
    HashMap<String, TextView> counts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // FeltEmotion List View setup
        addEmotionView = findViewById(R.id.addEmotionLayout);
        emotionHistory = findViewById(R.id.emotionListView);
        addEmotionButton = findViewById(R.id.addEmotionButton);
        emotionListController = new FeltEmotionListController(this);
        emotionListAdapter = new ArrayAdapter<FeltEmotion>(this, R.layout.list_item, emotionListController.getFeltEmotionArrayList());
        emotionHistory.setAdapter(emotionListAdapter);

        saveEmotionButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        commentText = findViewById(R.id.addCommentEditText);
        emotionSpinner = findViewById(R.id.emotionSelectionSpinner);
        spinnerAdapter = new ArrayAdapter<Emotion>(this, android.R.layout.simple_spinner_dropdown_item, Emotion.values());
        emotionSpinner.setAdapter(spinnerAdapter);

        loveCount = findViewById(R.id.loveCount);
        joyCount = findViewById(R.id.joyCount);
        surpriseCount = findViewById(R.id.surpriseCount);
        angerCount = findViewById(R.id.angerCount);
        sadnessCount = findViewById(R.id.sadnessCount);
        fearCount = findViewById(R.id.fearCount);

        counts = new HashMap<>();
        counts.put("Love", loveCount);
        counts.put("Joy", joyCount);
        counts.put("Surprise", surpriseCount);
        counts.put("Anger", angerCount);
        counts.put("Sadness", sadnessCount);
        counts.put("Fear", fearCount);

        setCounts();

        emotionHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                           int position, long id) {

                Intent intent = new Intent(adapterView.getContext(), EditOrDeleteEmotion.class);
                intent.putExtra(FELTEMOTION, position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        emotionListController.loadFromFile();
        emotionListAdapter.notifyDataSetChanged();
        setCounts();
    }

    public void slideDown(View view){
        view.setVisibility(view.INVISIBLE);
        view.setEnabled(false);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                view.getHeight());                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from below itself to its current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        view.setEnabled(true);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void addEmotionButtonClick(View view) {
        commentText.setText("");
        addEmotionButton.hide();
        emotionHistory.setEnabled(false);
        addEmotionButton.setEnabled(false);
        addEmotionView.setEnabled(true);
        saveEmotionButton.setEnabled(true);
        cancelButton.setEnabled(true);
        commentText.setEnabled(true);
        emotionSpinner.setEnabled(true);
        slideUp(addEmotionView);
    }

    public void saveEmotionButtonClick(View view){
        Emotion e = (Emotion) emotionSpinner.getSelectedItem();
        String c = commentText.getText().toString(); //TODO: throw error for comment > chars
        Calendar d = Calendar.getInstance();
        emotionListController.addFeltEmotion(new FeltEmotion(e, c, d));
        emotionListAdapter.notifyDataSetChanged();
        updateCount(e.toString());
        closeAddEmotionView(view);
    }

    public void closeAddEmotionView(View view){
        slideDown(addEmotionView);
        addEmotionButton.show();
        emotionHistory.setEnabled(true);
        addEmotionButton.setEnabled(true);
        addEmotionView.setEnabled(false);
        saveEmotionButton.setEnabled(false);
        cancelButton.setEnabled(false);
        commentText.setEnabled(false);
        emotionSpinner.setEnabled(false);
    }

    public void updateCount(String emotion){
        String s = emotionListController.getCount(emotion);
        counts.get(emotion).setText(s);
    }

    public void setCounts(){
        HashMap<String, String> countStrings = emotionListController.getCounts();

        for (Emotion e: Emotion.values()){
            counts.get(e.toString()).setText(countStrings.get(e.toString()));
        }
    }
}
