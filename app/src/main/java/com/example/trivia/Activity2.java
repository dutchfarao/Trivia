package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity implements QuizRequest.Callback{
    String selectedDuration;
    String selectedDifficulty;
    String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //get intent
        Intent intent = getIntent();
        selectedDuration = intent.getStringExtra("selectedDuration");
        selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        selectedCategory = intent.getStringExtra("selectedCategory");
        //get choices form user
        QuizRequest x = new QuizRequest(this, selectedDuration, selectedDifficulty, selectedCategory);
        x.getQuestions(this);

    }

    @Override
    public void gotQuestion(ArrayList<Question> menuList) {
    }

    @Override
    public void gotQuestionError(String message) {
    }
}

