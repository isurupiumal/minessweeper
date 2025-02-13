package com.minesweeper;


import java.util.Random;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Manages the Minesweeper grid: initializing cells, placing mines,
 * calculating adjacent mine counts, and revealing cells.
 */
public class Board {
    private int size;
    private int totalMines;
    private Cell[][] grid;

    public Board(int size, int totalMines) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size must be positive.");
        }
        int maxMines = (int)(size * size * 0.35);
        if (totalMines < 0 || totalMines > maxMines) {
            throw new IllegalArgumentException("Total mines must be between 0 and " + maxMines);
        }
        this.size = size;
        this.totalMines = totalMines;
        grid = new Cell[size][size];
        initializeCells();
        placeMines();
        calculateAdjacentMines();
    }

    private void initializeCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < totalMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (!grid[row][col].isMine()) {
                grid[row][col].setMine(true);
                minesPlaced++;
            }
        }
    }

    private void calculateAdjacentMines() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!grid[row][col].isMine()) {
                    int count = countAdjacentMines(row, col);
                    grid[row][col].setAdjacentMines(count);
                } else {
                    grid[row][col].setAdjacentMines(0);
                }
            }
        }
    }

    /**
     * Counts mines adjacent to the cell at (row, col).
     */
    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) continue;
                if (isValidCell(i, j) && grid[i][j].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Reveals the cell at (row, col). Returns true if a mine is hit.
     */
    public boolean revealCell(int row, int col) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        if (grid[row][col].isRevealed()) {
            return false; // already revealed
        }
        grid[row][col].reveal();
        if (grid[row][col].isMine()) {
            return true;
        }
        // If the cell has no adjacent mines, reveal surrounding cells.
        if (grid[row][col].getAdjacentMines() == 0) {
            floodFill(row, col);
        }
        return false;
    }

    /**
     * Recursively reveals adjacent cells that have no adjacent mines.
     */
    private void floodFill(int row, int col) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{row, col});
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];
            for (int i = r - 1; i <= r + 1; i++) {
                for (int j = c - 1; j <= c + 1; j++) {
                    if (isValidCell(i, j) && !grid[i][j].isRevealed() && !grid[i][j].isMine()) {
                        grid[i][j].reveal();
                        if (grid[i][j].getAdjacentMines() == 0) {
                            queue.add(new int[]{i, j});
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if all non-mine cells have been revealed.
     */
    public boolean isGameWon() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!grid[row][col].isMine() && !grid[row][col].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    /**
     * Displays the board.
     * @param revealMines if true, shows mines even if not revealed (used at game over)
     */
    public String displayBoard(boolean revealMines) {
        StringBuilder sb = new StringBuilder();
        // Print header row with column numbers.
        sb.append("  ");
        for (int col = 0; col < size; col++) {
            sb.append(" ").append(col + 1);
        }
        sb.append("\n");
        // Print rows with letter labels.
        for (int row = 0; row < size; row++) {
            char rowLabel = (char) ('A' + row);
            sb.append(rowLabel).append(" ");
            for (int col = 0; col < size; col++) {
                Cell cell = grid[row][col];
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        sb.append(" *");
                    } else {
                        sb.append(" ").append(cell.getAdjacentMines());
                    }
                } else {
                    if (revealMines && cell.isMine()) {
                        sb.append(" *");
                    } else {
                        sb.append(" _");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // The following methods are provided to support testing.

    /**
     * Manually sets a cell at (row, col) as a mine or not.
     */
    public void setMineAt(int row, int col, boolean isMine) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        grid[row][col].setMine(isMine);
    }

    /**
     * Recalculates adjacent mine counts. Useful when the board is modified during tests.
     */
    public void recalculateAdjacentMines() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!grid[row][col].isMine()) {
                    grid[row][col].setAdjacentMines(countAdjacentMines(row, col));
                } else {
                    grid[row][col].setAdjacentMines(0);
                }
            }
        }
    }
}