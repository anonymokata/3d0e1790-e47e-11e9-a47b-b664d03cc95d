package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

import static com.spenkana.wordsearch.membrane.result.Result.failureDueTo;

public class Puzzle {
    public final char[][] data;
    public final Integer sideLength;

    private Puzzle(String... rows) {
        sideLength = rows.length;
        data = new char[sideLength][sideLength];
        for (int row = 0; row < sideLength; ++row) {
            rows[row].getChars(
                    0, sideLength - 1, data[row], 0);
        }
    }

    public static Result<Puzzle, SimpleError> newPuzzle(String... rows) {
        if (rows == null || rows.length == 0) {
            return failureDueTo("Puzzle cannot be empty");

        }
        if(rows.length == 0){
            return failureDueTo("Puzzle cannot have zero width");
        }
        for(int i = 0; i < rows.length; ++i) {
            if (rows[i].length() != rows.length) {
                return failureDueTo("Puzzle must be square");
            }
        }
        return Result.successWith(new Puzzle(rows));
    }

    public Cell initial() {
        return new Cell(0, 0, data[0][0]);
    }

    class Cell {

        public final int x;
        public final int y;
        public final char value;

        public Cell(int x, int y, char value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}
