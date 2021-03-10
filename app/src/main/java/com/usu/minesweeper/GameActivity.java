package com.usu.minesweeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String gameMode = intent.getStringExtra("difficulty"); // TODO: Retrieve the game mode from the intent;
//        AppCompatTextView test = new AppCompatTextView(this);
//        test.setText(gameMode);
        GameView gameView = new GameView(this, gameMode);
        setContentView(gameView);
    }
}
