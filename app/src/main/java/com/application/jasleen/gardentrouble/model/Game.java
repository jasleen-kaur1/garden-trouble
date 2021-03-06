package com.application.jasleen.gardentrouble.model;

import java.util.Random;
import static java.lang.Boolean.TRUE;

/**
 * Game class supports the game logic which randomly picks which cells have rabbits.
 * Supports making the game board and scanning/ updating of cells.
 */

public class Game {

    private int NUM_ROWS;
    private int NUM_COLS;
    private int numberScans;
    private int numberRabbitsFound;
    private Cell[][] cellCollection;

    // Generates grid
    public void generateGrid(int cols, int rows, int numberRabbits){
         int NUM_RABBITS = numberRabbits;
         NUM_ROWS = rows;
         NUM_COLS = cols;
         cellCollection = new Cell[NUM_ROWS][NUM_COLS];

        //To generate random places for rabbits in the grid
        Random random = new Random();

        // Make grid of initial cells with default values
        for(int row=0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                cellCollection[row][col] = new Cell();
            }
        }
        // Placing random rabbits in their cells
        while (NUM_RABBITS > 0 ){
            int row = random.nextInt(NUM_ROWS);
            int col = random.nextInt(NUM_COLS);

            if (cellCollection[row][col].getHasRabbit() != TRUE ){
                cellCollection[row][col].setHasRabbit(TRUE);
                NUM_RABBITS --;
                initialScanGrid(col, row);
            }
        }
    }

    // Setting the scanning values for initial grid
    private void initialScanGrid(int col, int row){
        for(int initialCol=0; initialCol < NUM_COLS; initialCol++){
            if( cellCollection[row][initialCol]!= cellCollection[row][col]) {
                int initialColScannedRabbits = cellCollection[row][initialCol].getRowColumnNumberRabbits();
                initialColScannedRabbits++;
                cellCollection[row][initialCol].setRowColumnNumberRabbits(initialColScannedRabbits);
            }
        }
        for(int initialRow = 0; initialRow < NUM_ROWS; initialRow++){
            if( cellCollection[initialRow][col]!= cellCollection[row][col]) {
                int initialRowScannedRabbits = cellCollection[initialRow][col].getRowColumnNumberRabbits();
                initialRowScannedRabbits++;
                cellCollection[initialRow][col].setRowColumnNumberRabbits(initialRowScannedRabbits);
            }
        }
    }

    // Update number of rabbits found
    public int updateRabbitsFound() {
        return numberRabbitsFound;
    }

    // Check if rabbit is present
    public boolean checkIfRabbit(int col, int row){
        return cellCollection[row][col].getHasRabbit();
    }

    // Check if cell has been scanned before
    public boolean checkIfScanned(int col, int row){
        return (cellCollection[row][col].getIsScanned());
    }

    // Check if cell with rabbit has been clicked again
    public boolean rabbitCellClickedAgain(int col, int row){
        return (cellCollection[row][col].getRabbitCheckedOnce());
    }

    //Returns the total current number of rabbits in the cell's row and column
    public int currentStateRabbits(int col, int row){
        return cellCollection[row][col].getRowColumnNumberRabbits();
    }

    // Updates the number of scans made and scans cell
    public int updateScan(int col, int row){
        if (!checkIfScanned(col, row)) {
            numberScans++;
            cellCollection[row][col].setIsScanned(TRUE);
        }
        return numberScans;
    }

    // Updates all cells in the row and column of rabbit found
    public void updateCells(int col, int row) {

        cellCollection[row][col].setRabbitCheckedOnce(TRUE);
        numberRabbitsFound++;

        for (int scanCol = 0; scanCol < NUM_COLS; scanCol++) {
            if (cellCollection[row][scanCol] != cellCollection[row][col]) {
                int colScannedRabbits = cellCollection[row][scanCol].getRowColumnNumberRabbits();
                colScannedRabbits--;
                cellCollection[row][scanCol].setRowColumnNumberRabbits(colScannedRabbits);
            }
        }
        for (int scanRow = 0; scanRow < NUM_ROWS; scanRow++) {
            if (cellCollection[scanRow][col] != cellCollection[row][col]) {
                int rowScannedRabbits = cellCollection[scanRow][col].getRowColumnNumberRabbits();
                rowScannedRabbits--;
                cellCollection[scanRow][col].setRowColumnNumberRabbits(rowScannedRabbits);
            }
        }
    }
}


