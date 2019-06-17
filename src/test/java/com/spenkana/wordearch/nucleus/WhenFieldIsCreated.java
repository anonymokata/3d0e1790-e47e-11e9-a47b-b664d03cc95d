package com.spenkana.wordearch.nucleus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenFieldIsCreated {

    @Test
    public void widthIsCorrect() {
        for (int i = 0; i < 37; ++i) {

            Assertions.assertEquals(i, new Field(generateStringOfLength(i)).width);
        }
    }

    @Test
    public void heightIsCorrect(){

        for (int i = 1; i < 37; ++i) {
            String[] rows = new String[i];
            for(int j=0; j < i; ++j){
                rows[j] = "x";
            }
            assertEquals(i, new Field(rows).length);
        }

    }

    private String generateStringOfLength(int i) {
        char[] array = new char[i];
        Arrays.fill(array, 'x');
        return new String(array);
    }
}
