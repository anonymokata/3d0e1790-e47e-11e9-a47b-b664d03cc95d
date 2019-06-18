package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

import java.util.Objects;

import static com.spenkana.wordsearch.membrane.result.Result.failureDueTo;
import static com.spenkana.wordsearch.membrane.result.Result.successWith;

public class Puzzle {
    public final char[][] data;
    public final Integer sideLength;
    public final Cell initial;

    private Puzzle(String... rows) {
        sideLength = rows.length;
        data = new char[sideLength][sideLength];
        for (int row = 0; row < sideLength; ++row) {
            String line = rows[row];
            char[] chars = new char[sideLength];
            line.getChars(
                    0, sideLength, chars, 0);
            data[row] = chars;
        }

        initial = new Cell(0, 0, data[0][0]);
    }

    public static Result<Puzzle, SimpleError> newPuzzle(String... rows) {
        if (rows == null || rows.length == 0) {
            return failureDueTo("Puzzle cannot be empty");

        }
        if (rows.length == 0) {
            return failureDueTo("Puzzle cannot have zero width");
        }
        for (int i = 0; i < rows.length; ++i) {
            if (rows[i].length() != rows.length) {
                return failureDueTo("Puzzle must be square");
            }
        }
        return Result.successWith(new Puzzle(rows));
    }

    public Result<Cell, SimpleError> getCell(int x, int y) {
        return successWith(new Cell(x, y, data[y][x]));
    }

    class Cell {

        public final int x;
        public final int y;
        public final char value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x &&
                    y == cell.y &&
                    value == cell.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, value);
        }

        public Cell(int x, int y, char value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
