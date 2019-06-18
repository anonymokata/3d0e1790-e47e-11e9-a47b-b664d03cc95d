package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle.Cell;

import static com.spenkana.wordsearch.nucleus.Solver.Found.newFound;

public class Solver {

    private final Puzzle puzzle;

    public Solver(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public static Result<Solver, SimpleError> newScanner(Puzzle puzzle) {
        Solver solver = new Solver(puzzle);
        return Result.successWith(solver);
    }

    public Found[] find(String... words) {
        Cell[] cells = new Cell[]{puzzle.initial()};
        return new Found[]{newFound(words[0], cells)};
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
