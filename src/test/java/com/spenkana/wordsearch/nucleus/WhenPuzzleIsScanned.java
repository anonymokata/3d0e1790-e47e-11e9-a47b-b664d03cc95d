package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.nucleus.Scanner.Found;
import org.junit.jupiter.api.Test;


import static com.spenkana.wordsearch.nucleus.Puzzle.newPuzzle;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenPuzzleIsScanned {


    @Test
    public void singleCharWordIsFound() {
        String word = "E";
        Puzzle puzzle = newPuzzle(word).output;
        Scanner scanner = Scanner.newScanner(puzzle).output;

        Found[] wordsFound = scanner.find(word);

        Found found = wordsFound[0];
        assertEquals(found.word, word);
        assertEquals(1, found.cells.length);
        Puzzle.Cell cell = found.cells[0];
        assertEquals(0, cell.x);
        assertEquals(0, cell.y);
    }

    @Test
    public void horizontalScanSucceeds() {
        Puzzle puzzle = newPuzzle("AB", "CD").output;
    }


}
