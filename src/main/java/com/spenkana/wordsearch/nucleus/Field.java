package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

import static com.spenkana.wordsearch.membrane.result.Result.failureDueTo;

public class Field {
    public final char[][] data;
    public final Integer sideLength;

    private Field(String... rows) {
        sideLength = rows.length;
        data = new char[sideLength][sideLength];
        for (int row = 0; row < sideLength; ++row) {
            rows[row].getChars(
                    0, sideLength - 1, data[row], 0);
        }
    }

    public static Result<Field, SimpleError> newField(String... rows) {
        if (rows == null || rows.length == 0) {
            return failureDueTo("Field cannot be empty");

        }
        if(rows.length == 0){
            return failureDueTo("Field cannot have zero width");
        }
        for(int i = 0; i < rows.length; ++i) {
            if (rows[i].length() != rows.length) {
                return failureDueTo("Field must be square");
            }
        }
        return Result.successWith(new Field(rows));
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
