package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class FieldTestFunctions {
    static Field makeField(String... rows) {
        Result<Field, SimpleError> result = Field.newField(rows);
        if (result.failed){
            fail(result.error.message());
        }
        return result.output;
    }

    static String generateStringOfLength(int i) {
        char[] array = new char[i];
        Arrays.fill(array, 'x');
        return new String(array);
    }
}
