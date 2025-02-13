package com.minesweeper;

/**
 * Utility class to parse user input coordinates (e.g. "A1") into row and column indices.
 */
public class InputParser {
    /**
     * Parses the coordinate string and returns an array: {rowIndex, colIndex}.
     * @param input coordinate string (e.g. "A1")
     * @param boardSize size of the board (to validate bounds)
     * @return integer array with row and column indices.
     * @throws IllegalArgumentException if the input is invalid.
     */
    public static int[] parseCoordinate(String input, int boardSize) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid input format.");
        }
        input = input.trim().toUpperCase();
        char rowChar = input.charAt(0);
        int row = rowChar - 'A';
        if (row < 0 || row >= boardSize) {
            throw new IllegalArgumentException("Row out of bounds.");
        }
        String colPart = input.substring(1);
        int col;
        try {
            col = Integer.parseInt(colPart) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Column must be a number.");
        }
        if (col < 0 || col >= boardSize) {
            throw new IllegalArgumentException("Column out of bounds.");
        }
        return new int[]{row, col};
    }
}
