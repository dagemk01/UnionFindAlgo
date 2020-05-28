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
        grid = new State[n][n];
        this.n = n;
        size = n * n;
        open = 0;
        top_Virtual = size+1;
        bot_Virtual = size+2;
        driver = new WeightedQuickUnionUF(size + 2);

        for (int i = 1; i <= this.n; i++) {
            for (int j = 1; j <= this.n; j++) {
                grid[i][j] = State.CLOSED;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (validate(row, col)) {
            grid[row][col] = State.OPEN;

            int index = (int) Math.pow(size, row) + col;
            
            //Do two edge cases where if its at the top of the grid we connect to the top Virual point
            //Do the same for bottom virtual point for if the row is at the bottom. Easier percelation run   time.
            if (row == 1){
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
                    currIndex = (int) Math.pow(size, row - 1) + col;

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row - 1][col] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
                if (row + 1 < this.n) {
                    currIndex = (int) Math.pow(size, row + 1) + col;

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row + 1][col] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }

                // check the bounds of the col
                if (col - 1 >= 0) {
                    currIndex = (int) Math.pow(size, row) + (col - 1);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[row][col - 1] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
                if (col + 1 < this.n) {
                    currIndex = (int) Math.pow(size, row) + (col + 1);

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
        if (validate(row, col)) {
            if (grid[row][col] == State.OPEN) {
                return true;
            }
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return true;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return true;
    }

    // bounds check
    private boolean validate(int row, int col) {
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

    public static void main(String[] args) {
        System.out.println("hello");
    }
}