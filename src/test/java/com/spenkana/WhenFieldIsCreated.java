package com.spenkana;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenFieldIsCreated {

    @Test
    public void widthIsCorrect(){
        Field field = new Field("ABC");

        assertEquals(3, field.width);
    }

}
