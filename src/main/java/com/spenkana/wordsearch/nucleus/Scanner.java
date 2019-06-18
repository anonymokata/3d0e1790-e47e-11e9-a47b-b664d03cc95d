package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle.Cell;

import static com.spenkana.wordsearch.nucleus.Scanner.Found.newFound;

public class Scanner {

    private final Puzzle puzzle;

    public Scanner(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public static Result<Scanner, SimpleError> newScanner(Puzzle puzzle) {
        Scanner scanner = new Scanner(puzzle);
        return Result.successWith(scanner);
    }

    public Found[] find(String e) {
        Cell[] cells = new Cell[]{puzzle.initial()};
        return new Found[]{newFound(e, cells)};
    }

    public static class Found {

        public final String word;
        public final Cell[] cells;

        private Found(String word, Cell[] cells) {
            this.word = word;
            this.cells = cells;
        }

        public static Found newFound(String word, Cell[] cells) {
            return new Found(word, cells);
        }
    }
}
