package com.example.trivia;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScoreRequest implements Response.ErrorListener, Response.Listener<JSONArray>{
    Callback activity;
    Context context;
    int score;
    String userName;
    String duration;
    String difficulty;
    RequestQueue queue;

    // Constructor
    ScoreRequest(Context context1, int points, String name, String diff, String length) {
        this.context = context1;
        score = points;
        userName = name;
        difficulty = diff;
        duration = length;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //show error message if something goes wrong with request
        activity.gotScoresError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            //create arrayList with scores
            ArrayList<String> scoreboard = new ArrayList<>();
            // For each question
            for (int items = 0; items < response.length(); items = items + 1){
                JSONObject selected = (JSONObject) response.get(items);
                // getting all the data from scoreboard using keyword "Points"
                String points = selected.getString("Points");
                scoreboard.add(points);
            }
            Collections.sort(scoreboard, Collections.reverseOrder());
            activity.gotScores(scoreboard);
        }
        catch (JSONException exception){
            exception.printStackTrace();
        }
    }

    // Get the list
    void getScores(Callback callback){
        // First send points
        sendPoints(score, userName, difficulty, duration);

        // Start queue
        activity = callback;
        String url = "https://ide50-dutchfarao.legacy.cs50.io:8080/list";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, this, this);
        queue.add(jsonArrayRequest);
    }

    // Callback
public interface Callback { ;
    void gotScoresError(String message);
    void gotScores(ArrayList<String> scoreboard);
}

    // Sends your points to the server
    private void sendPoints(final int points, final String names, final String difficulty, final String duration) {
        // Code based on https://www.kompulsa.com/how-to-send-a-post-request-in-android/
        // POST the values
        String url = "https://ide50-dutchfarao.legacy.cs50.io:8080/list";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Success
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Success
                    }
                }) {
            protected Map<String, String> getParams() {
                // Put data in request
                Map<String, String> MyData = new HashMap<>();
                //MyData.put("Points", score + " points earned by: " + userName);
                MyData.put("Points", score + " / By: " + userName + " " + "(" + "Difficulty: " + difficulty + "," + " " + "Quiz length: " + duration + ")");
                return MyData;
            }
        };

        queue = Volley.newRequestQueue(context);
        queue.add(MyStringRequest);
    }
}
