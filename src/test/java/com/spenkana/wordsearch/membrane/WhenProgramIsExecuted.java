package com.spenkana.wordsearch.membrane;

import com.spenkana.wordsearch.PuzzleSpec;
import com.spenkana.wordsearch.nucleus.Puzzle;
import org.junit.jupiter.api.Test;

import static com.spenkana.wordsearch.ExpectedObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenProgramIsExecuted {

    @Test
    public void puzzleSpecIsReadCorrectly() {
        PuzzleSpec puzzleSpec = new Loader().load(
                "src/test/resources/puzzleSpecExample.txt"
        ).output;

        assertEquals(wordsExpectedInExample, puzzleSpec.wordsExpected);
        Puzzle puzzle = puzzleSpec.puzzle;
        assertEquals(ExamplePuzzle.sideLength, puzzle.sideLength);
        assertEquals(ExamplePuzzle.initial, puzzle.initial);
        for (int x = 0; x < puzzle.sideLength; ++x) {
            for (int y = 0; y < puzzle.sideLength; ++y) {
                assertEquals(ExamplePuzzle.getCell(x, y), puzzle.getCell(x, y));
            }
        }
    }


}
