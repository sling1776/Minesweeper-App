package com.usu.minesweeper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

public class difficultyButton extends AppCompatButton {
    String buttonName;

    public difficultyButton(String buttonName, OnClickListener listener, Context context){
        super(context);
        this.buttonName = buttonName;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(50,20,50,20);
        setLayoutParams(params);
        setText(buttonName);
        setOnClickListener(listener);

    }
}
