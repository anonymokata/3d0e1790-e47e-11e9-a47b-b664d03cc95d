package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle.Cell;

import java.util.LinkedList;
import java.util.List;

import static com.spenkana.wordsearch.membrane.result.Result.failureDueTo;
import static com.spenkana.wordsearch.membrane.result.Result.successWith;

public class Solver {

    private final Puzzle puzzle;

    public Solver(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public static Result<Solver, SimpleError> newScanner(Puzzle puzzle) {
        Solver solver = new Solver(puzzle);
        return successWith(solver);
    }

    public Found[] find(String... words) {
        LinkedList<Found> instancesFound = new LinkedList<>();
        String word = words[0];
        char[] chars = new char[word.length()];
        word.getChars(0, word.length(), chars, 0);
        Result<Cell[], SimpleError> result = findAll(
                chars, puzzle.initial, 0,
                new LinkedList<>()
        );
        if(result.succeeded){
            instancesFound.add(new Found(word, result.output));
        }
        return instancesFound.toArray(new Found[]{});
    }

    private Result<Cell[], SimpleError> findAll(
            char[] word, Cell currentCell,
            int wordCharIndex,
            List<Cell> cellsMatched) {
        if (currentCell.value == word[wordCharIndex]){
            cellsMatched.add(currentCell);
            int nextIndex = wordCharIndex + 1;
            if (nextIndex >= word.length){
                Cell[] allCellsMatched = cellsMatched.toArray(new Cell[0]);
                return successWith(allCellsMatched);
            }
            Result<Cell, SimpleError> result =
                    puzzle.getCell(currentCell.x + 1, currentCell.y);
            if (result.succeeded){
                return findAll(word, result.output, nextIndex, cellsMatched);
            }
        }
        return failureDueTo("Word not found: " + new String(word));
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
