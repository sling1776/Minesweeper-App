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
        // TODO: Draw the cell at its position depending on the state it is in

        if(!isSelected){
            if(isMarked){
                paint.setColor(Color.LTGRAY);
                canvas.drawRect((float)xPos, (float)yPos, (float)(xPos + width), (float)(yPos + height),paint);
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect((float)xPos, (float)yPos, (float)(xPos + width), (float)(yPos + height),paint);
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(10);
                canvas.drawLine((float)(xPos+5), (float)(yPos+5),(float)(xPos + (width/2)), (float)(yPos + height - 5), paint);
                canvas.drawLine((float)(xPos + (width/2)), (float)(yPos + height - 5), (float)(xPos + (width - 5)), (float)(yPos + 5), paint);
                canvas.drawLine((float)(xPos+5), (float)(yPos+5), (float)(xPos + (width - 5)), (float)(yPos + 5), paint);

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

        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
