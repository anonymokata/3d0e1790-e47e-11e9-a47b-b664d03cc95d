package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle.Cell;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.spenkana.wordsearch.membrane.result.Result.failureDueTo;
import static com.spenkana.wordsearch.membrane.result.Result.successWith;

public class Solver {

    private final Puzzle puzzle;

    public Solver(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public List<Found> find(String... words) {
        List<Found> instancesFound = new LinkedList<>();
        for (String word : words) {
            findInstancesOfWord(instancesFound, word);
        }
        return Collections.unmodifiableList(instancesFound);
    }

    private void findInstancesOfWord(List<Found> instancesFound, String word) {
        char[] chars = new char[word.length()];
        word.getChars(0, word.length(), chars, 0);
        Cell possibleStartingCell = puzzle.initial;
        boolean cellsRemain = true;
        while (cellsRemain) {
            if (possibleStartingCell.value == chars[0]) {
                instancesFound.addAll(
                        findAll(
                                word, possibleStartingCell, 0,
                                new LinkedList<>()
                        ));
            }
            Result<Cell, SimpleError> result = getNextCell(possibleStartingCell);
            if (result.succeeded) {
                possibleStartingCell = result.output;
            } else {
                cellsRemain = false;
            }
        }
    }

    private List<Found> findAll(
            String word, Cell currentCell,
            int wordCharIndex,
            List<Cell> cellsMatched) {
        List<Found> instancesFound = new LinkedList<>();
        if (currentCell.value == word.charAt(wordCharIndex)) {
            cellsMatched.add(currentCell);
            int nextIndex = wordCharIndex + 1;
            if (nextIndex != word.length()) {
                findRemaining(word, currentCell, cellsMatched, instancesFound, nextIndex);
            } else {
                instancesFound.add(
                        Found.newFound(word, cellsMatched.toArray(new Cell[0])));
            }
        }
        return instancesFound;
    }

    private void findRemaining(
            String word, Cell currentCell, List<Cell> cellsMatched,
            List<Found> instancesFound, int nextIndex
    ) {
        for (Neighbor neighbor : Neighbor.asList()) {
            Result<Cell, SimpleError> result =
                    puzzle.getCell(
                            currentCell.x + neighbor.xIncrement,
                            currentCell.y + neighbor.yIncrement);
            if (result.succeeded) {
                instancesFound.addAll(
                        findAll(word, result.output, nextIndex, cellsMatched)
                );
            }
        }
    }

    Result<Cell, SimpleError> getNextCell(Cell currentCell) {
        int x = currentCell.x + 1;
        if (x < puzzle.sideLength) {
            return successWith(puzzle.getCell(x, currentCell.y).output);
        }
        int y = currentCell.y + 1;
        if (y < puzzle.sideLength) {
            return successWith(puzzle.getCell(0, y).output);
        }
        return failureDueTo("No more cells");
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

    private enum Neighbor {
        Right(1, 0),
        Left(-1, 0),
        Down(0, 1);

        public final int xIncrement;
        public final int yIncrement;
        static List<Neighbor> neighbors;

        Neighbor(int xIncrement, int yIncrement) {
            this.xIncrement = xIncrement;
            this.yIncrement = yIncrement;
            register(this);
        }

        private static void register(Neighbor neighbor) {
            if (neighbors == null) {
                neighbors = new LinkedList<>();
            }
            neighbors.add(neighbor);
        }

        public static List<Neighbor> asList() {
            return Collections.unmodifiableList(neighbors);
        }
    }
}
