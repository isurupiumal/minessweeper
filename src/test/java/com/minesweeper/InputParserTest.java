package com.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InputParser.
 */
public class InputParserTest {

    @Test
    public void testValidInput() {
        int[] coords = InputParser.parseCoordinate("B2", 5);
        assertEquals(1, coords[0]); // 'B' -> index 1
        assertEquals(1, coords[1]); // "2" -> index 1
    }

    @Test
    public void testValidInputLowerCase() {
        int[] coords = InputParser.parseCoordinate("c3", 5);
        assertEquals(2, coords[0]); // 'C' -> index 2
        assertEquals(2, coords[1]); // "3" -> index 2
    }

    @Test
    public void testInvalidInputTooShort() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                InputParser.parseCoordinate("A", 5));
        assertTrue(exception.getMessage().contains("Invalid input format"));
    }

    @Test
    public void testInvalidRow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                InputParser.parseCoordinate("Z1", 5));
        assertTrue(exception.getMessage().contains("Row out of bounds"));
    }

    @Test
    public void testInvalidColumn() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                InputParser.parseCoordinate("A10", 5));
        assertTrue(exception.getMessage().contains("Column out of bounds"));
    }
}
