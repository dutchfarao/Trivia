package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class Activity1 extends AppCompatActivity {
    String selectedDuration;
    String selectedDifficulty;
    String selectedCategory;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        //due to androidstudio bug drawable wasn't being shown so i chose to do this by code.
        logo = findViewById(R.id.imageView1);
        logo.setImageResource(R.drawable.knowledge);

        //setting up list with duration as content, hardcoded
        String[] arrayDuration = new String[]{
                "Short", "Medium", "Long"
        };
        Spinner spinnerDuration = findViewById(R.id.SpinnerDuration1);

        //setting up list with difficulty as content, hardcoded
        String[] arrayDifficulty = new String[]{
                "Easy", "Medium", "Hard"
        };
        Spinner spinnerDifficulty = findViewById(R.id.SpinnerDifficulty1);

        //setting up list with category as content, hardcoded
        String[] arrayCategory = new String[]{
                "General Knowledge", "Science & Nature", "Sports", "Vehicles"
        };
        Spinner spinnerCategory = findViewById(R.id.SpinnerCategory1);

        //set adapter 1
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayDuration);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(adapter1);
        //set adapter 2
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayDifficulty);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapter2);
        //set adapter 3
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayCategory);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter3);

        //set spinnerclick listener 1
        onSpinnerClick1 onSpinnerClick1 = new onSpinnerClick1();
        spinnerDuration.setOnItemSelectedListener(onSpinnerClick1);
        //set spinnerclick listener 2
        onSpinnerClick2 onSpinnerClick2 = new onSpinnerClick2();
        spinnerDifficulty.setOnItemSelectedListener(onSpinnerClick2);
        //set spinnerclick listener 3
        onSpinnerClick3 onSpinnerClick3 = new onSpinnerClick3();
        spinnerCategory.setOnItemSelectedListener(onSpinnerClick3);
        //set buttonclick listener
        onClick onClick = new onClick();
        Button btn = findViewById(R.id.Button1);
        btn.setOnClickListener(onClick);


    }

    //create 'onclick' for spinners
    private class onSpinnerClick1 implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            selectedDuration = item.toLowerCase();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    private class onSpinnerClick2 implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            selectedDifficulty = item.toLowerCase();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    private class onSpinnerClick3 implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            selectedCategory = item.toLowerCase();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    //creation of onclick for button
    private class onClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //set intent and start new activity
            Intent intent = new Intent(Activity1.this, Activity2.class);
            intent.putExtra("selectedDuration", selectedDuration);
            intent.putExtra("selectedDifficulty", selectedDifficulty);
            intent.putExtra("selectedCategory", selectedCategory);
            startActivity(intent);
        }
    }


}
