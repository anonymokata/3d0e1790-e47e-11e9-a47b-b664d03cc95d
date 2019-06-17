package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

public class Field {
    public final Integer width;
    public final Integer length;

    private Field(String... rows) {
        width = rows[0].length();
        length = rows.length;
    }

    public static Result<Field, SimpleError> newField(String... rows) {
        if(rows == null){
            return Result.failureDueTo("Field cannot be empty");

        }
        if (rows[0].length() == 0){
            return Result.failureDueTo("Width cannot be zero");
        }
        return Result.successWith(new Field(rows));
    }
}
