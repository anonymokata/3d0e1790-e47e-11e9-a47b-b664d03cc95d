package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;

import java.util.ArrayList;
import java.util.List;

import static com.spenkana.wordsearch.nucleus.Scanner.Found.newFound;

public class Scanner {

    private final Field field;

    public Scanner(Field field) {
        this.field = field;
    }

    public static Result<Scanner, SimpleError> newScanner(Field field) {
        Scanner scanner = new Scanner(field);
        return Result.successWith(scanner);
    }

    public List<Found> find(String e) {
        ArrayList<Found> wordsFound = new ArrayList<>();
        wordsFound.add(newFound(e));
        return wordsFound;
    }

    public static class Found {

        public final String word;

        private Found(String word) {
            this.word = word;
        }

        public static Found newFound(String word) {
            return new Found(word);
        }
    }
}
