package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class Activity4 extends AppCompatActivity implements ScoreRequest.Callback{
    String userName;
    ListView listView;
    int scoreInt;
    String selectedDuration;
    String selectedDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        scoreInt = intent.getIntExtra("scoreInt", scoreInt);
        selectedDuration = intent.getStringExtra("selectedDuration");
        selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        //find listView
        listView = findViewById(R.id.listView4);
        // create ScoreRequest to post score and get scores
        ScoreRequest scorelist = new ScoreRequest(this, scoreInt, userName, selectedDifficulty, selectedDuration);
        scorelist.getScores(this);
    }

    @Override
    public void gotScoresError(String message) {
        //show error message if something went wrong with ScoreRequest
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotScores(ArrayList<String> scoreboard) {
        //create and set arrayadapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, scoreboard);
        listView.setAdapter(adapter);
    }

    //this makes sure that activity 1 is loaded when user presses back button in activity4
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity4.this, Activity1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

