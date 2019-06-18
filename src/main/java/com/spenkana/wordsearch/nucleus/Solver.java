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
        Cell possibleStartingCell = puzzle.initial;
        boolean cellsRemain = true;
        while (cellsRemain) {
            if (possibleStartingCell.value == chars[0]) {
                Result<Cell[], SimpleError> searchResult = findAll(
                        chars, possibleStartingCell, 0,
                        new LinkedList<>()
                );
                if (searchResult.succeeded) {
                    instancesFound.add(new Found(word, searchResult.output));
                }
            }
            Result<Cell, SimpleError> result = getNextCell(possibleStartingCell);
            if (result.succeeded) {
                possibleStartingCell = result.output;
            } else {
                cellsRemain = false;
            }
        }
        return instancesFound.toArray(new Found[0]);
    }

    Result<Cell, SimpleError> getNextCell(Cell currentCell) {
        int x = currentCell.x + 1;
        if (x < puzzle.sideLength) {
            return successWith(puzzle.getCell(x, currentCell.y).output);
        }
        int y = currentCell.y + 1;
        if (y < puzzle.sideLength){
            return successWith(puzzle.getCell(0, y).output);
        }
                return failureDueTo("No more cells");
    }

    private Result<Cell[], SimpleError> findAll(
            char[] word, Cell currentCell,
            int wordCharIndex,
            List<Cell> cellsMatched) {
        if (currentCell.value == word[wordCharIndex]) {
            cellsMatched.add(currentCell);
            int nextIndex = wordCharIndex + 1;
            if (nextIndex >= word.length) {
                Cell[] allCellsMatched = cellsMatched.toArray(new Cell[0]);
                return successWith(allCellsMatched);
            }
            Result<Cell, SimpleError> result =
                    puzzle.getCell(currentCell.x + 1, currentCell.y);
            if (result.succeeded) {
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
