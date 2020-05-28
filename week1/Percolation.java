//package week1;

import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    enum State {
        OPEN, CLOSED
    }

    // instance variables
    State[][] grid;
    int size;
    int open;
    int n;
    int top_Virtual;
    int bot_Virtual;
    WeightedQuickUnionUF driver;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        validate(n);
        grid = new State[n][n];
        this.n = n;
        size = n * n;
        open = 0;
        top_Virtual = size;
        bot_Virtual = size+1;
        driver = new WeightedQuickUnionUF(size + 2);

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                grid[i][j] = State.CLOSED;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        //transform the row and col
        row--;
        col--;

        if (validate(row, col)) {
            grid[row][col] = State.OPEN;

            int index = getIndex( row, col);
            
            //Do two edge cases where if its at the top of the grid we connect to the top Virual point
            //Do the same for bottom virtual point for if the row is at the bottom. Easier percelation run   time.
            if (row == 0){
                driver.union(index, top_Virtual);
            }

            if(row == size){
                driver.union(index, bot_Virtual);
            }
            int currIndex;
            // checking if the current index is not open
            // if its not open then we will open it and cinnect it with other squares in the
            // area.
            // if its open we do nothing
            if (!this.isOpen(row, col)) {
                grid[row][col] = State.OPEN;
                open++;
                // check the boounds of the row
                if (row - 1 >= 0) {
                    currIndex = getIndex(row-1, col);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row - 1][col] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
                if (row + 1 < this.n) {
                    currIndex = getIndex(row+1, col);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row + 1][col] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }

                // check the bounds of the col
                if (col - 1 >= 0) {
                    currIndex = getIndex(row, col-1);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row][col - 1] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
                if (col + 1 < this.n) {
                    currIndex = getIndex(row, col+1);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row][col + 1] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        //transform the row and col
        row--;
        col--;

        if (validate(row, col)) {
            if (grid[row][col] == State.OPEN) {
                return true;
            }
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        //transform the row and col
        row--;
        col--;

        validate(row, col);
        int index = getIndex(row, col);
        if (driver.find(index) == driver.find(top_Virtual)){
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        if(driver.find(top_Virtual)==driver.find(bot_Virtual)){
            return true;
        }
        return false;
    }

    // bounds check
    private boolean validate(int row, int col) {
        //transform the row and col
        row--;
        col--;
        if (row > 0 && row <= this.n) {
            if (col > 0 && col <= this.n) {
                return true;
            }
        }
        throw new IllegalArgumentException("index " + row + " or index " + col + " is not between 1 and " + (this.n));
        
    }

    private boolean validate(int n) {
        if (n  > 0) {
            return true;
        }
        throw new IllegalArgumentException("Cannot be initialized to " + n);
    }
    private int getIndex( int row, int col){
  
        int index = (int) Math.pow(size, row) + col;
        
        return index;
    }
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        Percolation test = new Percolation(3);
        System.out.println(test.isOpen( row, col));
        
    }
}