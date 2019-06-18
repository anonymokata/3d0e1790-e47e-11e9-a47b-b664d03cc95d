package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.spenkana.wordsearch.nucleus.Field.newField;
import static com.spenkana.wordsearch.nucleus.FieldTestFunctions.generateStringOfLength;
import static org.junit.jupiter.api.Assertions.*;

public class WhenFieldIsCreated {

    @Test
    public void factoryMethodIsProvided() {
        Result<Field, SimpleError> result = newField("ABC", "DEF","GHI");

        assertEquals(3, result.output.sideLength);
    }

    @Test
    public void nonSquareFieldCannotBeCreated() {
        Result<Field, SimpleError> result = newField("ABC", "DEF");

        assertFalse(result.succeeded);
        assertEquals("Field must be square", result.error.message());

        result = newField("ABC", "DEF", "GH");

        assertFalse(result.succeeded);
        assertEquals("Field must be square", result.error.message());
    }


    @Test
    public void zeroWidthFieldCannotBeCreated() {
        Result<Field, SimpleError> result = newField(new String[]{});

        assertFalse(result.succeeded);
        assertEquals("Field cannot be empty", result.error.message());
    }

    @Test
    public void emptyFieldCannotBeCreated() {
        Result<Field, SimpleError> result = newField(null);

        assertFalse(result.succeeded);
        assertEquals("Field cannot be empty", result.error.message());

    }


    @Test
    public void sideLengthIsCorrect() {
        for (int expectedSideLength = 1; expectedSideLength < 37; ++expectedSideLength) {
            String[] rows = new String[expectedSideLength];
            String row = generateStringOfLength(expectedSideLength);
            for(int i = 0; i < expectedSideLength; ++i){
                rows[i] = row;
            }
            Field field = newField(rows).output;

            Assertions.assertEquals(expectedSideLength, field.sideLength);
        }
    }


}
