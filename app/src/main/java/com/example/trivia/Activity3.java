package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {
    String score;
    String userName;
    int scoreInt;
    EditText UserInput;
    TextView Score;
    Button submit;
    String selectedDuration;
    String selectedDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        //get intent
        Intent intent = getIntent();
        score = intent.getStringExtra("score");
        scoreInt = intent.getIntExtra("scoreInt", scoreInt);
        selectedDuration = intent.getStringExtra("selectedDuration");
        selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        UserInput = findViewById(R.id.EditText3);
        Score = findViewById(R.id.Score3);
        Score.setText(score);
        //set button listener
        submit = findViewById(R.id.Button3);
        onClick onButtonClick = new onClick();
        submit.setOnClickListener(onButtonClick);
    }

    private class onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //get username from EditText
            userName = String.valueOf(UserInput.getText());
            //start next activity
            Intent intent = new Intent(Activity3.this, Activity4.class);
            intent.putExtra("userName", userName);
            intent.putExtra("scoreInt", scoreInt);
            intent.putExtra("selectedDifficulty", selectedDifficulty);
            intent.putExtra("selectedDuration", selectedDuration);
            startActivity(intent);
        }
    }
}