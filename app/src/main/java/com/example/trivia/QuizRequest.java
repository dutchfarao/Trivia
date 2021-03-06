package com.example.trivia;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;

public class QuizRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    String duration;
    String difficulty;
    String category;
    JSONArray questions;
    String url;
    Callback activity;
    public String question;
    public String correct_answer;
    public JSONArray incorrect_answers;
    public ArrayList<Question> qList;

    @Override
    public void onErrorResponse(VolleyError error) {
        getQuestions(activity);
        activity.gotQuestionError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        qList = new ArrayList<>();
        //create empty JSONArray
        questions = new JSONArray();
        //fill JSONArray with data from from url
        try {
            questions = response.getJSONArray("results");
            //iterate over objects
            for (int i = 0; i < questions.length(); i++) {
                JSONObject object = questions.getJSONObject(i);
                //create Question object
                Question Quizquestion = new Question();
                //fill object with info
                question = object.getString("question");
                correct_answer = object.getString("correct_answer");
                incorrect_answers = object.getJSONArray("incorrect_answers");
                //get individual questions from incorrect_questions array
                ArrayList <String> incorrect_answersList = new ArrayList<>();
                for (int j = 0; j < 3; j++){
                    incorrect_answersList.add(j, String.valueOf(incorrect_answers.get(j)));
                }
                Quizquestion.setQuestion(question);
                Quizquestion.setCorrect_answer(correct_answer);
                Quizquestion.setIncorrect_answers(incorrect_answersList);
                //add filled object to list
                qList.add(Quizquestion);
            }
            activity.gotQuestion(qList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getQuestions(Callback activity){
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        //the api url always starts with this, after that, depending on what the user has chosen,
        //the url is build up accordingly
        url = "https://opentdb.com/api.php?amount=";
        //duration
        if (duration.equals("short")){
            //5 questions
            url = url + "5";
        }
        else if(duration.equals("medium")){
            //10 questions
            url = url + "10";
        }
        else if (duration.equals("long")) {
            //15 questions
            url = url + "15";
        }
        //category
        if (category.equals("General Knowledge")) {
            url = url + "&category=9";
        }
        else if (category.equals("Science & Nature")) {
            url = url + "&category=17";
        }
        else if (category.equals("Sports")) {
            url = url + "&category=21";
        }
        else if (category.equals("Vehicles")) {
            url = url + "&category=28";
        }
        //difficulty
        if (difficulty.equals("easy")) {
            url = url + "&difficulty=easy&type=multiple";
        }
        if (difficulty.equals("medium")) {
            url = url + "&difficulty=medium&type=multiple";
        }
        if (difficulty.equals("hard")) {
            url = url + "&difficulty=hard&type=multiple";
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    public interface Callback {
        void gotQuestion(ArrayList<Question> questionList);
        void gotQuestionError(String message);
    }

    public QuizRequest(Context context, String duration1, String difficulty1, String category1) {
        this.context = context;
        duration = duration1;
        difficulty = difficulty1;
        category = category1;
    }
}




