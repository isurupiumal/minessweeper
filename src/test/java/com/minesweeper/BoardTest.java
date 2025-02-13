package com.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Board class.
 */
public class BoardTest {

    @Test
    public void testBoardInitialization() {
        Board board = new Board(4, 3);
        assertEquals(4, board.getSize());
        // Count mines in board.
        int mineCount = 0;
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (grid[i][j].isMine()) {
                    mineCount++;
                }
            }
        }
        assertEquals(3, mineCount);
    }

    @Test
    public void testAdjacentMinesCalculation() {
        Board board = new Board(3, 0); // Start with no mines.
        // Manually set mines.
        board.setMineAt(0, 0, true);
        board.setMineAt(1, 1, true);
        board.recalculateAdjacentMines();
        // Check adjacent count for cell (0,1): should be 2 mines.
        Cell cell01 = board.getGrid()[0][1];
        assertEquals(2, cell01.getAdjacentMines());
        // Check adjacent count for cell (2,2): should be 1 mine.
        Cell cell22 = board.getGrid()[2][2];
        assertEquals(1, cell22.getAdjacentMines());
    }

    @Test
    public void testRevealCellAndFloodFill() {
        // Create a board with no mines so that flood fill reveals the entire board.
        Board board = new Board(3, 0);
        board.revealCell(1, 1);
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                assertTrue(grid[i][j].isRevealed(), "Cell (" + i + "," + j + ") should be revealed.");
            }
        }
    }

    @Test
    public void testGameWonCondition() {
        // Create a board with one mine.
        Board board = new Board(3, 1);
        Cell[][] grid = board.getGrid();
        // Reveal all non-mine cells.
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!grid[i][j].isMine()) {
                    board.revealCell(i, j);
                }
            }
        }
        assertTrue(board.isGameWon(), "Game should be won when all non-mine cells are revealed.");
    }
}