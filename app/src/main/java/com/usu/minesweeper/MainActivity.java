package com.usu.minesweeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        AppCompatTextView welcomeText = new AppCompatTextView(this);
        welcomeText.setText(getResources().getText(R.string.app_name));
        welcomeText.setTextColor(Color.BLACK);
        welcomeText.setTextSize(40);
        welcomeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        AppCompatButton easyButton = new difficultyButton("Easy", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                String difficulty = "easy";
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        },this);
        easyButton.setBackgroundColor(Color.rgb(100,200,100));

        AppCompatButton mediumButton = new difficultyButton("Intermediate", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                String difficulty = "intermediate";
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        },this);
       mediumButton.setBackgroundColor(Color.rgb(100,100,200));

        AppCompatButton hardButton =  new difficultyButton("Expert", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                String difficulty = "expert";
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        },this);
        hardButton.setBackgroundColor(Color.rgb(200,100,100));


        mainLayout.addView(welcomeText);
        mainLayout.addView(easyButton);
        mainLayout.addView(mediumButton);
        mainLayout.addView(hardButton);

        setContentView(mainLayout);

    }
}