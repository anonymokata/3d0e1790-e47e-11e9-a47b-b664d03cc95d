package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.spenkana.wordsearch.nucleus.Puzzle.newPuzzle;
import static com.spenkana.wordsearch.nucleus.FieldTestFunctions.generateStringOfLength;
import static org.junit.jupiter.api.Assertions.*;

public class WhenPuzzleIsCreated {

    @Test
    public void factoryMethodIsProvided() {
        Result<Puzzle, SimpleError> result = newPuzzle("ABC", "DEF", "GHI");

        assertEquals(3, result.output.sideLength);
    }

    @Test
    public void initialCellIsOrigin() {
        Puzzle puzzle = newPuzzle("ABC", "DEF", "GHI").output;
        Cell initial = puzzle.initial;

        assertEquals(0, initial.x);
        assertEquals(0, initial.y);
        assertEquals('A', (initial.value));
    }

    @Test
    public void nonSquareFieldCannotBeCreated() {
        Result<Puzzle, SimpleError> result = newPuzzle("ABC", "DEF");

        assertFalse(result.succeeded);
        assertEquals("Puzzle must be square", result.error.message());

        result = newPuzzle("ABC", "DEF", "GH");

        assertFalse(result.succeeded);
        assertEquals("Puzzle must be square", result.error.message());
    }


    @Test
    public void zeroWidthFieldCannotBeCreated() {
        Result<Puzzle, SimpleError> result = newPuzzle(new String[]{});

        assertFalse(result.succeeded);
        assertEquals("Puzzle cannot be empty", result.error.message());
    }

    @Test
    public void emptyFieldCannotBeCreated() {
        Result<Puzzle, SimpleError> result = newPuzzle((String[])null);

        assertFalse(result.succeeded);
        assertEquals("Puzzle cannot be empty", result.error.message());

    }


    @Test
    public void sideLengthIsCorrect() {
        for (int expectedSideLength = 1; expectedSideLength < 37; ++expectedSideLength) {
            String[] rows = new String[expectedSideLength];
            String row = generateStringOfLength(expectedSideLength);
            for (int i = 0; i < expectedSideLength; ++i) {
                rows[i] = row;
            }
            Puzzle puzzle = newPuzzle(rows).output;

            Assertions.assertEquals(expectedSideLength, puzzle.sideLength);
        }
    }


}
