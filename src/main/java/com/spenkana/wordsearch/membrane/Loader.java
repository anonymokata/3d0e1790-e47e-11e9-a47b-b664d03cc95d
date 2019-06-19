package com.spenkana.wordsearch.membrane;

import com.spenkana.wordsearch.PuzzleSpec;
import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.spenkana.wordsearch.nucleus.Puzzle.newPuzzle;

public class Loader {
    public Result<PuzzleSpec, SimpleError> load(String filePath) {
        int maxLen = 1000;
        char[] buffer = new char[maxLen];
        try {
            String[] lines = readFile(filePath, maxLen, buffer);

            Result<Puzzle, SimpleError> result = buildPuzzle(lines);
            if (result.failed){
                return Result.failureDueTo(result.error.message());
            }
            return Result.successWith(
                    new PuzzleSpec(lines[0], result.output));
        } catch (Exception e) {
            return Result.failureDueTo(e.getLocalizedMessage());
        }

    }

    private Result<Puzzle, SimpleError> buildPuzzle(String[] lines) {
        String[] puzzleLines = new String[lines.length - 1];
        for(int i = 1; i < lines.length; ++i){
            puzzleLines[i - 1] = lines[i].replace(",","");
        }
        return newPuzzle(puzzleLines);
    }

    private String[] readFile(String filePath, int maxLen, char[] buffer) throws IOException {
        FileReader reader;
        reader = new FileReader(filePath);
        reader.read(buffer, 0, maxLen);
        String raw = new String(buffer);
        String trimmed = raw.substring(0, raw.indexOf(0));
        return trimmed.split("\n");
    }
}
