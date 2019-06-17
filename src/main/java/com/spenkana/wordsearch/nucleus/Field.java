package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SafeError;

public class Field {
    public final Integer width;
    public final Integer length;

    private Field(String... rows) {
        width = rows[0].length();
        length = rows.length;
    }

    public static Result<Field, SafeError> newField(String... rows) {
        return Result.successWith(new Field(rows));
    }
}
