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

    public String[] solveFor(String[] expectedWords) {
        List<Found> instancesFound = findStraightInstancesOnly(expectedWords);
        int size = instancesFound.size();
        String[] formatted = new String[size];
        for(int i = 0; i < size; ++i){
            formatted[i] = format(instancesFound.get(i));
        }
        return formatted;
    }

    private static String format(Found found){
        StringBuilder sb = new StringBuilder();
        sb.append(found.word)
                .append(": ");
        for (Cell cell : found.cells){
            sb.append('(')
                    .append(cell.x)
                    .append(',')
                    .append(cell.y)
                    .append(')')
                    .append(',');
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        return sb.toString();
    }

    public List<Found> findStraightInstancesOnly(String[] words) {
        List<Found> allInstances = find(words);
        List<Found> straightInstances = new LinkedList<>();
        for(Found found : allInstances){
            if(isStraight(found)){
                straightInstances.add(found);
            }
        }
        return Collections.unmodifiableList(straightInstances);
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
            List<Cell> cellsMatchedSoFar) {
        List<Found> instancesFound = new LinkedList<>();
        if (currentCell.value == word.charAt(wordCharIndex)) {
            cellsMatchedSoFar.add(currentCell);
            int nextIndex = wordCharIndex + 1;
            if (nextIndex == word.length()) {
                instancesFound.add(
                        Found.newFound(word, cellsMatchedSoFar.toArray(new Cell[0])));
                return instancesFound;
            }
            for (NeighborOffset neighborOffset : NeighborOffset.allNeighborOffsets()) {
                List<Cell> prefix = new LinkedList<>();
                prefix.addAll(cellsMatchedSoFar);
                Result<Cell, SimpleError> result =
                        puzzle.getCell(
                                currentCell.x + neighborOffset.xIncrement,
                                currentCell.y + neighborOffset.yIncrement);
                if (result.succeeded) {
                    instancesFound.addAll(
                            findAll(word, result.output, nextIndex, prefix)
                    );
                }
            }
        }
        return instancesFound;
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

    private static boolean isStraight(Found found) {
        Cell[] cells = found.cells;
        if (cells.length == 1){
            return true;
        }
        int xOffsetExpected = cells[1].x - cells[0].x;
        int yOffsetExpected = cells[1].y - cells[0].y;
        for (int i = 1; i < cells.length; ++i){
            if (yOffsetExpected != (cells[i].y - cells[i-1].y)){
                return false;
            }
            if (xOffsetExpected != (cells[i].x - cells[i-1].x)){
                return false;
            }
        }
        return true;
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

    private enum NeighborOffset {
        Right(1, 0),
        Left(-1, 0),
        Down(0, 1),
        Up(0, -1),
        DownRight(1, 1),
        UpLeft(-1, -1),
        UpRight(1, -1),
        DownLeft(-1, 1);

        public final int xIncrement;
        public final int yIncrement;
        static List<NeighborOffset> neighborOffsets;

        NeighborOffset(int xIncrement, int yIncrement) {
            this.xIncrement = xIncrement;
            this.yIncrement = yIncrement;
            register(this);
        }

        private static void register(NeighborOffset neighborOffset) {
            if (neighborOffsets == null) {
                neighborOffsets = new LinkedList<>();
            }
            neighborOffsets.add(neighborOffset);
        }

        public static List<NeighborOffset> allNeighborOffsets() {
            return Collections.unmodifiableList(neighborOffsets);
        }
    }
}
