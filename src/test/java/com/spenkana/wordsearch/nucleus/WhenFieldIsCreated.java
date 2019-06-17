package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SafeError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WhenFieldIsCreated {

    @Test
    public void factoryMethodIsProvided() {
        Result<Field, SafeError> result = Field.newField("ABC");

        assertEquals(3, result.output.width);
    }


    @Test
    public void widthIsCorrect() {
        for (int i = 0; i < 37; ++i) {

            Assertions.assertEquals(i,
                    makeField(generateStringOfLength(i)).width);
        }
    }

    private Field makeField(String... rows) {
        Result<Field, SafeError> result = Field.newField(rows);
        if (result.failed){
            fail(result.error.message());
        }
        return result.output;
    }

    @Test
    public void heightIsCorrect(){

        for (int i = 1; i < 37; ++i) {
            String[] rows = new String[i];
            for(int j=0; j < i; ++j){
                rows[j] = "x";
            }
            assertEquals(i, makeField(rows).length);
        }

    }

    private String generateStringOfLength(int i) {
        char[] array = new char[i];
        Arrays.fill(array, 'x');
        return new String(array);
    }
}
