package com.usu.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.service.quickaccesswallet.GetWalletCardsCallback;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class GameView extends View {
    Game game;
    Paint paint = new Paint();
    String gameMode;
    public GameView(Context context, String gameMode) {
        super(context);
        this.gameMode = gameMode;

        GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                game.handleTap(motionEvent);
                invalidate();
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                game.handleLongPress(motionEvent);
                invalidate();
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });




        setOnTouchListener((view, e) -> {
            // TODO: use your gesture detector here.
            gestureDetectorCompat.onTouchEvent(e);
            return true;
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        game = new Game(gameMode, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        game.draw(canvas, paint);
    }
}
