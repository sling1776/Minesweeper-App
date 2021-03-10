package com.usu.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private enum State {
        PLAY,
        WIN,
        LOSE,
    }

    Cell[][] cells;
    int mineCount;
    int rows = 30;
    int cols = 16;
    double cellWidth;
    double cellHeight;
    int screenWidth;
    int screenHeight;
    State state = State.PLAY;

    public Game(String gameMode, int screenWidth, int screenHeight) {
        cells = new Cell[rows][cols];
        if (gameMode.equals("expert")) {
            mineCount = 100;
        } else if (gameMode.equals("intermediate")) {
            mineCount = 50;
        } else {
            mineCount = 10;
        }
        cellHeight = (double)screenHeight / rows;
        cellWidth = (double)screenWidth / cols;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        initCells();
    }

    private void initCells() {

        ArrayList<Boolean> mineArray = new ArrayList<>();

        for( int i = 0 ; i <  mineCount; i++){
            mineArray.add(true);
        }
        while(mineArray.size() < (rows * cols)){
            mineArray.add(false);
        }
        Collections.shuffle(mineArray);

        //Place mines in cells
        for( int i = 0 ; i < rows ; i ++)
        {
            for (int j = 0; j < cols; j++) {
                boolean mineValue = mineArray.get(0);
                mineArray.remove(0);
                if (mineValue) {
                    double width = screenWidth/cols;
                    double height = screenHeight/rows;
                    cells[i][j] = new Cell(j *width, i*height, width, height, Cell.Type.MINE);
                } else {
                    double width = screenWidth/cols;
                    double height = screenHeight/rows;
                    cells[i][j] = new Cell(j * width, i * height, width, height, Cell.Type.EMPTY);
                }
            }
        }
        //set up surrounding number cells
        for( int i = 0 ; i < rows ; i ++)
        {
            for (int j = 0; j < cols; j++) {
                int num = countNeighbors(i, j, cells);
                if(!(num == 0) && !(cells[i][j].getType() == Cell.Type.MINE)){
                    cells[i][j].setType(Cell.Type.NUMBER);
                    cells[i][j].setNumNeighbors(num);
                }
            }
        }

    }


    private int countNeighbors(int row, int col, Cell[][] cells) {

        int count = 0;
        int left = col - 1;
        int right = col + 1;
        int top = row - 1;
        int bottom = row+1;

        if(left >= 0){
            if(top >= 0) if(cells[top][left].getType() == Cell.Type.MINE) count ++;
            if(cells[row][left].getType() == Cell.Type.MINE) count ++;
            if(bottom < rows) if(cells[bottom][left].getType() == Cell.Type.MINE) count ++;
        }
        if(right < cols){
            if(top >= 0) if(cells[top][right].getType() == Cell.Type.MINE) count ++;
            if(cells[row][right].getType() == Cell.Type.MINE) count ++;
            if(bottom < rows) if(cells[bottom][right].getType() == Cell.Type.MINE) count ++;
        }

        if(top >= 0) if(cells[top][col].getType() == Cell.Type.MINE) count ++;
        if(bottom < rows) if(cells[bottom][col].getType() == Cell.Type.MINE) count ++;


        return count;
    }

    private void revealMines() {
        for(int i = 0 ; i< rows;  i++){
            for (int j  = 0 ; j < cols ; j++){
                if (cells[i][j].getType()== Cell.Type.MINE) cells[i][j].select();
            }
        }
    }

    private void explodeBlankCells(int row, int col) {
        if((row >= rows) || (col >= cols) ) return;
        if((row < 0) || (col < 0)) return;
        if(cells[row][col].isSelected()) return;
        if(cells[row][col].isMarked()) return;
        cells[row][col].select();
        if(cells[row][col].getType() == Cell.Type.NUMBER)return;

        explodeBlankCells(row - 1, col - 1);
        explodeBlankCells(row - 1, col);
        explodeBlankCells(row - 1, col + 1);
        explodeBlankCells(row, col - 1);
        explodeBlankCells(row , col + 1);
        explodeBlankCells(row + 1, col - 1);
        explodeBlankCells(row + 1, col);
        explodeBlankCells(row + 1, col + 1);


    }


    public void handleTap(MotionEvent e) {
        if(state == State.PLAY) {

            float tapX = e.getX();
            float tapY = e.getY();

            float cellX = tapX / (float) cellWidth;
            float cellY = tapY / (float) cellHeight;

            Cell chosenCell = cells[(int) cellY][(int) cellX];
            if(!chosenCell.isMarked()){
                if (chosenCell.getType() == Cell.Type.MINE) {
                    revealMines();
                    state = State.LOSE;
                } else if (chosenCell.getType() == Cell.Type.EMPTY) {
                    explodeBlankCells((int) cellY, (int) cellX);
                } else {
                    chosenCell.select();
                }
            }

        }
    }

    public void handleLongPress(MotionEvent e) {
        // TODO: find the cell and toggle its mark
        //       then check to see if the player won the game
        if(state == State.PLAY) {
            float tapX = e.getX();
            float tapY = e.getY();

            float cellX = tapX / (float) cellWidth;
            float cellY = tapY / (float) cellHeight;

            Cell chosenCell = cells[(int) cellY][(int) cellX];
            if(!chosenCell.isSelected())chosenCell.toggleMark();
            checkWin();
        }
    }

    private void checkWin(){
        int minesMarked = 0;
        int incorrectMark = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <cols ; j++) {
                if(cells[i][j].isMarked() && (cells[i][j].getType() == Cell.Type.MINE)){
                    minesMarked ++;
                }
                else if(cells[i][j].isMarked() && !(cells[i][j].getType() == Cell.Type.MINE)){
                    incorrectMark++;
                }

            }
        }
        if(minesMarked == mineCount && incorrectMark == 0){
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <cols ; j++) {
                    if(!cells[i][j].isMarked() && !cells[i][j].isSelected()){
                        cells[i][j].select();
                    }
                }
            }
            state = State.WIN;
        }
    }


    public void draw(Canvas canvas, Paint paint) {
        for(int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].draw(canvas, paint);
                paint.reset();
            }
        }

        if (state == State.WIN) {
            // TODO draw a win screen here
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            canvas.drawText("YOU WIN",screenWidth/8, screenHeight/2,paint );
            paint.reset();
        }
        if (state == State.LOSE) {
            // TODO draw a win screen here
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            canvas.drawText("YOU LOSE",screenWidth/11, screenHeight/2, paint );
            paint.reset();
        }
    }
}
