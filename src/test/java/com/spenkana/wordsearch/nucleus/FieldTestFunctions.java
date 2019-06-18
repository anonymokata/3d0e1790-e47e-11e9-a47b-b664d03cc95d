package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class FieldTestFunctions {
    static Puzzle makePuzzle(String... rows) {
        Result<Puzzle, SimpleError> result = Puzzle.newPuzzle(rows);
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
