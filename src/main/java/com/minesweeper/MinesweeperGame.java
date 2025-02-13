package com.minesweeper;

import java.util.Scanner;

/**
 * Main game controller for Minesweeper.
 * Handles user interaction and game flow.
 */
public class MinesweeperGame {
    private Board board;
    private boolean gameOver;

    public MinesweeperGame(Board board) {
        this.board = board;
        this.gameOver = false;
    }

    /**
     * Starts a game session using the provided Scanner.
     * This method handles user input and displays the updated minefield after each move.
     * It also prints the number of adjacent mines for the selected square.
     *
     * @param scanner the Scanner instance to use for reading user input.
     */
    public void start(Scanner scanner) {

        System.out.println("Here is your minefield:");
        System.out.println(board.displayBoard(false));

        while (!gameOver) {

            System.out.print("Select a square to reveal (e.g. A1): ");
            String input;
            try {
                input = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error reading input: " + e.getMessage());
                continue;
            }
            int[] coords;
            try {
                coords = InputParser.parseCoordinate(input, board.getSize());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
                continue;
            }
            boolean hitMine = board.revealCell(coords[0], coords[1]);
            if (hitMine) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                gameOver = true;
                break;
            } else {
                int adjacent = board.getGrid()[coords[0]][coords[1]].getAdjacentMines();
                System.out.println("This square contains " + adjacent + " adjacent mines.");
                System.out.println("Here is your updated minefield:");
                System.out.println(board.displayBoard(false));
            }
            if (board.isGameWon()) {
                System.out.println("Congratulations, you have won the game!");
                gameOver = true;
                break;
            }
        }
        // Note: We do not close the scanner here so that System.in remains open for further input.
    }

    /**
     * Main method – entry point to start the game.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        while (playAgain) {
            System.out.println("Welcome to Minesweeper!");
            int size = 0;
            int mines = 0;
            // Prompt for grid size.
            while (true) {
                System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
                String input = scanner.nextLine();
                try {
                    size = Integer.parseInt(input.trim());
                    if (size <= 0) {
                        System.out.println("Grid size must be a positive integer.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Please enter a positive integer.");
                }
            }
            // Calculate maximum mines allowed (35% of total cells).
            int maxMines = (int) (size * size * 0.35);
            while (true) {
                System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
                String input = scanner.nextLine();
                try {
                    mines = Integer.parseInt(input.trim());
                    if (mines < 0 || mines > maxMines) {
                        System.out.println("Number of mines must be between 0 and " + maxMines + ".");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Please enter an integer.");
                }
            }
            Board board = new Board(size, mines);
            MinesweeperGame game = new MinesweeperGame(board);
            game.start(scanner);
            System.out.println("Press any key to play again...");
            String choice = scanner.nextLine();
            if (choice.trim().equalsIgnoreCase("exit")) {
                playAgain = false;
            }
        }
        scanner.close();
    }
}