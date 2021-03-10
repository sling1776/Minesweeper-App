package com.usu.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

    // FEEL FREE TO CHANGE THESE!
    private int[] colors = {
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.rgb(0,0,100),
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK
    };

    public enum Type {
        MINE,
        NUMBER,
        EMPTY
    }

    private boolean isMarked = false;
    private boolean isSelected = false;
    private Type type;
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private int numNeighbors = 0;
    public Cell(double xPos, double yPos, double width, double height, Type type) {
        this.type = type;
        this.yPos = yPos;
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void toggleMark() {
        isMarked = !isMarked;
    }

    public void select() {
        isSelected = true;
        isMarked = false;
    }

    public void setNumNeighbors(int numNeighbors) {
        this.numNeighbors = numNeighbors;
    }

    public void draw(Canvas canvas, Paint paint) {
        if(!isSelected){
            if(isMarked){
                int margin = 10;
                paint.setColor(Color.LTGRAY);
                canvas.drawRect((float)xPos, (float)yPos, (float)(xPos + width), (float)(yPos + height),paint);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float)xPos, (float)yPos, (float)(xPos + width), (float)(yPos + height),paint);
                paint.setColor(Color.rgb(100,200,100));
                paint.setStrokeWidth(10);
                canvas.drawLine((float)(xPos+margin), (float)(yPos+margin),(float)(xPos + (width/2)), (float)(yPos + height - margin), paint);
                canvas.drawLine((float)(xPos + (width/2)), (float)(yPos + height - margin), (float)(xPos + (width - margin)), (float)(yPos + margin), paint);
                canvas.drawLine((float)(xPos+margin), (float)(yPos+margin), (float)(xPos + (width - margin)), (float)(yPos + margin), paint);
                paint.reset();
            }else {
                paint.setColor(Color.GRAY);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.reset();
            }
        }
        else{
            if(getType() == Type.EMPTY){
                paint.setColor(Color.WHITE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.reset();

            } else if(getType() == Type.MINE){
                int margin = 10;
                paint.setColor(Color.WHITE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.setStrokeWidth(10);
                paint.setColor(Color.RED);
                canvas.drawLine((float)(xPos + margin), (float)(yPos+margin),(float)(xPos + width - margin), (float)(yPos + height - margin), paint);
                canvas.drawLine((float)(xPos + width - margin), (float)(yPos + margin), (float)(xPos + margin), (float)(yPos + height - margin), paint);
                paint.reset();
            } else {
                int margin = 10;
                paint.setColor(Color.WHITE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
                paint.setColor(colors[numNeighbors-1]);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(50);
                canvas.drawText(""+numNeighbors, (float)(xPos+(width/3)), (float)(yPos +height - margin), paint);
                paint.reset();
            }
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
