package com.minesweeper;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MinesweeperGameTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Test scenario where the player selects a square that hits a mine.
     * The Board is mocked so that when "A1" (which maps to [0,0]) is revealed, it returns true.
     * The test asserts that the game prints the game-over message.
     */
    @Test
    public void testGameOverWhenMineHit() {
        // Simulate user input "A1" (which will be parsed to coordinate [0, 0]).
        String simulatedInput = "A1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Create a mock Board.
        Board board = mock(Board.class);

        // Stub board behavior.
        when(board.getSize()).thenReturn(3);
        when(board.displayBoard(false)).thenReturn("Fake Board Display");
        // For coordinate "A1" ([0,0]), simulate hitting a mine.
        when(board.revealCell(0, 0)).thenReturn(true);
        // isGameWon is not needed in this test, as a mine hit ends the game.

        MinesweeperGame game = new MinesweeperGame(board);
        game.start(scanner);

        String output = outContent.toString();
        assertTrue(output.contains("Oh no, you detonated a mine! Game over."),
                "Game over message not found in output.");
    }

    /**
     * Test scenario where the player makes a safe move that results in a win.
     * The Board is mocked so that for input "B2" ([1,1]), the cell is safe.
     * A dummy grid is provided so that getAdjacentMines() returns a count (here, 2).
     * The board is also stubbed to report a win after the move.
     */
    @Test
    public void testGameWinWhenNoMineHit() {
        // Simulate user input "B2" (which maps to coordinate [1, 1]).
        String simulatedInput = "B2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Create a mock Board.
        Board board = mock(Board.class);

        // Stub board behavior.
        when(board.getSize()).thenReturn(3);
        // Simulate initial and updated board displays.
        when(board.displayBoard(false))
                .thenReturn("Initial Board Display")
                .thenReturn("Updated Board Display");
        // For coordinate "B2" ([1,1]), simulate a safe move.
        when(board.revealCell(1, 1)).thenReturn(false);
        // Create a dummy grid of Cell objects.
        // Assume Cell is defined elsewhere and has a method getAdjacentMines().
        Cell[][] grid = new Cell[3][3];
        // Create a mock cell that returns 2 adjacent mines.
        Cell safeCell = mock(Cell.class);
        when(safeCell.getAdjacentMines()).thenReturn(2);
        // Populate the grid.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = safeCell;
            }
        }
        when(board.getGrid()).thenReturn(grid);
        // Simulate that the game is won after the safe move.
        when(board.isGameWon()).thenReturn(true);

        MinesweeperGame game = new MinesweeperGame(board);
        game.start(scanner);

        String output = outContent.toString();
        assertTrue(output.contains("Congratulations, you have won the game!"),
                "Winning message not found in output.");
    }
}
