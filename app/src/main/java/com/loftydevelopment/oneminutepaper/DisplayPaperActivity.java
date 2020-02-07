package com.loftydevelopment.oneminutepaper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayPaperActivity extends AppCompatActivity {

    TextView tvSubject, tvMainIdeas, tvQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_paper);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        tvSubject = findViewById(R.id.textViewSubject);
        tvMainIdeas = findViewById(R.id.textViewMainIdeas);
        tvQuestions = findViewById(R.id.textViewQuestions);

        String editedTitle = intent.getStringExtra("titles");
        editedTitle = editedTitle.replace("(", "\n");
        editedTitle = editedTitle.replace(")", "");

        tvSubject.setText(editedTitle);
        tvMainIdeas.setText(intent.getStringExtra("mainIdeas"));
        tvQuestions.setText(intent.getStringExtra("questions"));

        Typeface font = Typeface.createFromAsset(getAssets(), "PatrickHand-Regular.ttf");
        tvSubject.setTypeface(font);
        tvMainIdeas.setTypeface(font);
        tvQuestions.setTypeface(font);

        tvMainIdeas.setMovementMethod(new ScrollingMovementMethod());
        tvQuestions.setMovementMethod(new ScrollingMovementMethod());


    }

    public void backOnClick(View view){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        int page = 1;
        intent.putExtra("One", page);// One is your argument

        startActivity(intent);

    }
}
