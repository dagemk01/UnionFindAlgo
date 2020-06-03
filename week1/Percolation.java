//package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private enum State {
        OPEN, CLOSED
    }

    // instance variables
    private State[][] grid;
    private final int size;
    private int open;
    private final int n;
    private final int top_Virtual;
    private final int bot_Virtual;
    private final WeightedQuickUnionUF driver;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        validate(n);
        grid = new State[n][n];
        this.n = n;
        size = n * n;
        open = 0;
        top_Virtual = 0;
        bot_Virtual = size;
        driver = new WeightedQuickUnionUF(size + 1);

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                grid[i][j] = State.CLOSED;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // transform the row and col
        row--;
        col--;

        if (validate(row, col)) {
            // checking if the current index is not open
            // if its not open then we will open it and cinnect it with other squares in the
            // area.
            // if its open we do nothing
            if (grid[col][row] == State.CLOSED) {
                int currIndex;
                grid[col][row] = State.OPEN;
                open++;
                // grid[col][row] = State.OPEN;

                int index = getIndex(row, col);

                // Do two edge cases where if its at the top of the grid we connect to the top
                // Virual point
                // Do the same for bottom virtual point for if the row is at the bottom. Easier
                // percelation run time.
                if (row == 0) {
                    driver.union(index, top_Virtual);
                }

                if (row == this.n - 1) {
                    driver.union(index, bot_Virtual);
                }

                // check the boounds of the row
                if (row - 1 >= 0) {
                    currIndex = getIndex(row - 1, col);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[col][row - 1] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
                if (row + 1 < this.n) {
                    currIndex = getIndex(row + 1, col);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[col][row + 1] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }

                // check the bounds of the col
                if (col - 1 >= 0) {
                    currIndex = getIndex(row, col - 1);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[col - 1][row] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
                if (col + 1 < this.n) {
                    currIndex = getIndex(row, col + 1);

                    // check to see if we need to union any of the surrounding cells
                    if (grid[col + 1][row] == State.OPEN) {
                        driver.union(index, currIndex);
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // transform the row and col
        row--;
        col--;

        if (validate(row, col)) {
            if (grid[col][row] == State.OPEN) {
                return true;
            }
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // transform the row and col
        row--;
        col--;

        validate(row, col);
        int index = getIndex(row, col);
        if (grid[col][row] == State.OPEN){
            if (driver.find(index) == driver.find(top_Virtual)) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        if (driver.find(top_Virtual) == driver.find(bot_Virtual)) {
            return true;
        }
        return false;
    }

    // bounds check
    private boolean validate(int row, int col) {
        // transform the row and col

        if (row >= 0 && row < this.n) {
            if (col >= 0 && col < this.n) {
                return true;
            }
        }
        throw new IllegalArgumentException("index " + row + " or index " + col + " is not between 1 and " + (this.n));

    }

    private boolean validate(int n) {
        if (n > 0) {
            return true;
        }
        throw new IllegalArgumentException("Cannot be initialized to " + n);
    }

    private int getIndex(int row, int col) {
        int index = (n * row) + col;
        // System.out.println(index);

        return index;
    }

    public static void main(String[] args) {
        int row = 1;
        int col = 1;
        Percolation test = new Percolation(25);
        System.out.println(test.isOpen(row, col));
        test.open(1, 1);
        test.open(2, 1);
        test.open(2, 3);

        System.out.println(test.isFull(1, 1));
        System.out.println(test.isFull(2, 1));
        System.out.println(test.isFull(2, 3));

        System.out.println(test.isOpen(1, 1));
        System.out.println(test.isOpen(2, 2));
        System.out.println(test.isOpen(2, 3));
        System.out.println(test.isOpen(2, 1));

        System.out.println(test.percolates());
        test.open(3, 1);
        System.out.println(test.percolates());

    }
}