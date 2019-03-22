package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {
    String score;
    EditText UserInput;
    TextView Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Intent intent = getIntent();
        score = intent.getStringExtra("score");
        UserInput = findViewById(R.id.EditText3);
        Score = findViewById(R.id.Score3);
        Score.setText(score);
    }
}
