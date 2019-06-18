package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.nucleus.Solver.Found;
import org.junit.jupiter.api.Test;


import static com.spenkana.wordsearch.nucleus.Puzzle.newPuzzle;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenPuzzleIsScanned {


    @Test
    public void singleCharWordIsFound() {
        String word = "E";
        Puzzle puzzle = newPuzzle(word).output;
        Solver solver = Solver.newScanner(puzzle).output;

        Found[] wordsFound = solver.find(word);

        Found found = wordsFound[0];
        assertEquals(found.word, word);
        assertEquals(1, found.cells.length);
        Puzzle.Cell cell = found.cells[0];
        assertEquals(puzzle.initial, cell);
    }

    @Test
    public void horizontalScanSucceedsAtOrigin() {
        String word = "AB";
        Puzzle puzzle = newPuzzle(word, "CD").output;
        Solver solver = Solver.newScanner(puzzle).output;

        Found[] wordsFound = solver.find(word);

        Found found = wordsFound[0];
        assertEquals(found.word, word);
        assertEquals(2, found.cells.length);
        assertEquals(puzzle.initial, found.cells[0]);
        assertEquals(puzzle.getCell(1, 0).output, found.cells[1]);
    }

    @Test
    public void horizontalScanSucceedsAnywhereOnFirstLine() {
        String word = "BC";
        Puzzle puzzle = newPuzzle("ABC","DEF","GHI").output;
        Solver solver = Solver.newScanner(puzzle).output;

        Found[] wordsFound = solver.find(word);

        Found found = wordsFound[0];
        assertEquals(found.word, word);
        assertEquals(2, found.cells.length);
        assertEquals(puzzle.getCell(1, 0).output, found.cells[0]);
        assertEquals(puzzle.getCell(2, 0).output, found.cells[1]);

    }


}
