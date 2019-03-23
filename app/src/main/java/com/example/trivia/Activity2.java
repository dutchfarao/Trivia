package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
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
        selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        selectedCategory = intent.getStringExtra("selectedCategory");

        //get questions according to the options user has chosen
        QuizRequest x = new QuizRequest(this, selectedDuration, selectedDifficulty, selectedCategory);
        x.getQuestions(this);

        //create button listeners
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
        //get arraylist of questions
        qList = qList1;
        //get number of questions, we'll use this later
        qlistSize = qList.size();
        //current question is the question which the user is answering at the moment
        currentQuestion = qList.get(count);
        //calling interface method
        InterfaceCreator(count);


    }

    public void InterfaceCreator(int i) {
        i = count;
        currentQuestion = qList.get(i);
        //get question
        questioni = currentQuestion.getQuestion();
        //get correct answers and incorrect answers and then shuffle them
        correct_answeri = currentQuestion.getCorrect_answer();
        randomAnswers = currentQuestion.getIncorrect_answers();
        randomAnswers.add(correct_answeri);
        Collections.shuffle(randomAnswers);
        //setting text, implementing html to display text correctly.
        Answer1.setText(Html.fromHtml(randomAnswers.get(0)));
        Answer2.setText(Html.fromHtml(randomAnswers.get(1)));
        Answer3.setText(Html.fromHtml(randomAnswers.get(2)));
        Answer4.setText(Html.fromHtml(randomAnswers.get(3)));
        Question2.setText(Html.fromHtml(questioni));
    }

    @Override
    public void gotQuestionError(String message) {
        //set toast in case of no questions
        Toast.makeText(this, "Something wrong with connection.", Toast.LENGTH_LONG).show();

    }

    //creation of onclick method for button
    private class onClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            count++;
            buttonClicked = String.valueOf(v.getContentDescription());
            //check which button has been clicked by user
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

            //if answer is correct, add score, according to the level of difficulty user has chosen.
            if (correct_answeri.equals(UserChoice)) {
                if (selectedDifficulty.equals("easy")){
                    score++;
                }
                if(selectedDifficulty.equals("medium")){
                    score = score + 2;
                }
                if (selectedDifficulty.equals("hard")){
                    score = score + 3;
                }
                Score2= findViewById(R.id.Score2);
                scoreString = String.valueOf(score);
                //set score in appropriate textView
                Score2.setText("Score: " + scoreString);
            }
            //if all questions have been answered, continue to next activity
            if (count == qlistSize){
                Intent intent = new Intent(Activity2.this, Activity3.class);
                intent.putExtra("score", scoreString);
                intent.putExtra("scoreInt", score);
                intent.putExtra("selectedDifficulty", selectedDifficulty);
                intent.putExtra("selectedDuration", selectedDuration);
                startActivity(intent);
            }
            //else, continue with next question
            else if(count < qlistSize ) {
                InterfaceCreator(count);
            }
        }
    }
}

