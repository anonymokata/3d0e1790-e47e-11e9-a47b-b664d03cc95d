package com.spenkana.wordsearch.nucleus;

import org.junit.jupiter.api.Test;


import java.util.List;

import static com.spenkana.wordsearch.nucleus.Field.newField;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhenFieldIsScanned {


    @Test
    public void singleCharWordIsFound() {
        String word = "E";
        Field field = newField(word).output;
        Scanner scanner = Scanner.newScanner(field).output;

        List<Scanner.Found> found = scanner.find(word);

        assertEquals(found.get(0).word, word);
    }


}
