package com.example.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class Activity2 extends AppCompatActivity implements QuizRequest.Callback{
    String selectedDuration;
    String selectedDifficulty;
    String selectedCategory;
    TextView Question2;
    TextView Score2;
    String buttonClicked;
    String scoreString;
    int count;
    int answerUser;
    int qlistSize;
    Button Answer1;
    Button Answer2;
    Button Answer3;
    Button Answer4;
    public ArrayList<Question> qList = new ArrayList<>();
    public Question currentQuestion;
    int score = 0;
    String questioni;
    String correct_answeri;
    ArrayList<String> randomAnswers;
    String UserChoice;


    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Answer1 = findViewById(R.id.Answer1);
        Answer2 = findViewById(R.id.Answer2);
        Answer3 = findViewById(R.id.Answer3);
        Answer4 = findViewById(R.id.Answer4);
        Question2 = findViewById(R.id.Question2);
        //get intent
        Intent intent = getIntent();
        selectedDuration = intent.getStringExtra("selectedDuration");
        Log.i(TAG, "selected duration in first activity:" + selectedDuration);
        selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        Log.i(TAG, "selected difficulty in first activity:" + selectedDifficulty);
        selectedCategory = intent.getStringExtra("selectedCategory");
        Log.i(TAG, "selected category in first activity:" + selectedCategory);

        //get choices form user
        QuizRequest x = new QuizRequest(this, selectedDuration, selectedDifficulty, selectedCategory);
        x.getQuestions(this);

        //create button listeners
        //create listener for button

        onClick onButtonClick = new onClick();
        onClick onButtonClick2 = new onClick();
        onClick onButtonClick3 = new onClick();
        onClick onButtonClick4 = new onClick();
        Answer1.setOnClickListener(onButtonClick);
        Answer2.setOnClickListener(onButtonClick2);
        Answer3.setOnClickListener(onButtonClick3);
        Answer4.setOnClickListener(onButtonClick4);



    }

    @Override
    public void gotQuestion(ArrayList<Question> qList1) {
        qList = qList1;
        qlistSize = qList.size();
        Log.d(TAG, "qlistsize: " + qlistSize);
        currentQuestion = qList.get(count);
        uiSetter(count);


    }

    public void uiSetter(int i) {
        i = count;
        currentQuestion = qList.get(i);
        questioni = currentQuestion.getQuestion();
        correct_answeri = currentQuestion.getCorrect_answer();
        randomAnswers = currentQuestion.getIncorrect_answers();
        randomAnswers.add(correct_answeri);
        Collections.shuffle(randomAnswers);
        Answer1.setText(randomAnswers.get(0));
        Answer2.setText(randomAnswers.get(1));
        Answer3.setText(randomAnswers.get(2));
        Answer4.setText(randomAnswers.get(3));
        Question2.setText(questioni);

    }

    @Override
    public void gotQuestionError(String message) {
        Toast.makeText(this, "No connection.", Toast.LENGTH_LONG).show();

    }

    //creation of onclick method for button
    private class onClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            count++;
            buttonClicked = String.valueOf(v.getContentDescription());
            switch (buttonClicked){
                case "button1":
                    answerUser = 0;
                    break;
                case "button2":
                    answerUser = 1;
                    break;
                case "button3":
                    answerUser = 2;
                    break;
                case "button4":
                    answerUser = 3;
                    break;
            }


            UserChoice = randomAnswers.get(answerUser);
            Log.d(TAG, "UserChoise: " + UserChoice);
            Log.d(TAG, "answerUser: " + answerUser);


            if (correct_answeri.equals(UserChoice)) {
                score++;
                Log.d(TAG, "Score: " + score);
                Score2= findViewById(R.id.Score2);
                scoreString = String.valueOf(score);
                Score2.setText("Score: " + scoreString);
            }
            if (count == qlistSize){
                Intent intent = new Intent(Activity2.this, Activity3.class);
                intent.putExtra("score", scoreString);
                startActivity(intent);
            }
            else if(count < qlistSize ) {
                uiSetter(count);
            }




        }
    }


}

