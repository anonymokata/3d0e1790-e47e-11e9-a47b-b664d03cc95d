package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.nucleus.Field.Cell;
import org.junit.jupiter.api.Test;

import static com.spenkana.wordsearch.nucleus.FieldTestFunctions.makeField;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenFieldIsScanned {

    @Test
    public void initialCellIsOrigin() {
        Field field = makeField("ABC", "DEF", "GHI");
        Cell initial = field.initial();

        assertEquals(0, initial.x);
        assertEquals(0, initial.y);
        assertEquals('A', (initial.value));
    }


}
