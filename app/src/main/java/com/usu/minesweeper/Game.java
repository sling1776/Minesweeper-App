package com.usu.minesweeper;

import android.graphics.Canvas;
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
       // TODO: create all cells, randomly assigning cells to be mines depending on difficulty.
        // HINT: 1. Create an ArrayList of Booleans
        //      2. set the first n (where n is number of mines you want) to be true
        //      3. set the remaining to be false (the total number of items in the list should be rows * cols)
        //      4. then shuffle the array list using Collections.shuffle()
        //      5. Then you can use this arraylist like a queue when iterating of your grid

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
                if(!(num == 0)){
                    cells[i][j].setType(Cell.Type.NUMBER);
                    cells[i][j].setNumNeighbors(num);
                }
            }
        }

        cells[1][1].toggleMark();
    }


    private int countNeighbors(int row, int col, Cell[][] cells) {
        // TODO: Count how many mines surround the cell at (row, col);
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
        // TODO: loop through the cells and select all mines
        for(int i = 0 ; i< rows;  i++){
            for (int j  = 0 ; j < cols ; j++){
                if (cells[i][j].getType()== Cell.Type.MINE) cells[i][j].select();
            }
        }
    }

    private void explodeBlankCells(int row, int col) {
        // TODO: recursively select all surrounding cells, only stopping when
        //      you reach a cell that has already been selected,
        //      or when you select a cell that is not Empty
        if(!(row < rows) && !(col < cols) ) return;
        if(!(row > 0) && !(col > 0)) return;
        cells[row][col].select();
        if(cells[row][col].getType() == Cell.Type.EMPTY){
            explodeBlankCells(row - 1, col - 1);
            explodeBlankCells(row - 1, col);
            explodeBlankCells(row - 1, col + 1);
            explodeBlankCells(row, col - 1);
            explodeBlankCells(row , col + 1);
            explodeBlankCells(row + 1, col - 1);
            explodeBlankCells(row + 1, col);
            explodeBlankCells(row + 1, col + 1);

        }
    }


    public void handleTap(MotionEvent e) {
        // TODO: find the cell the player tapped
        //      Depending on what type of cell they tapped
        //         mine: select the cell, reveal the mines, and set the game to the LOSE state
        //         empty cell: select the cell and explode the surrounding cells
        //         all others: simply select the cell
    }

    public void handleLongPress(MotionEvent e) {
        // TODO: find the cell and toggle its mark
        //       then check to see if the player won the game
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
        }
    }
}
