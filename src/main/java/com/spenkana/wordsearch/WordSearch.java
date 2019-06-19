package com.spenkana.wordsearch;

import com.spenkana.wordsearch.membrane.Loader;
import com.spenkana.wordsearch.membrane.result.Result;
import com.spenkana.wordsearch.membrane.result.SimpleError;
import com.spenkana.wordsearch.nucleus.Puzzle;
import com.spenkana.wordsearch.nucleus.Solver;

import static java.lang.System.exit;

public class WordSearch {
    public static void main(String[] args) {
        if (args.length == 0) {
            errorOut(
                    "First argument must be filepath for puzzle specification.", 1);
        }
        String specFilePath = args[0];
        Result<PuzzleSpec, SimpleError> result = new Loader().load(specFilePath);
        if (result.failed) {
            errorOut(result.error.message(), 2);
        }
        Puzzle puzzle = result.output.puzzle;
        String[] wordsExpected = result.output.wordsExpected;

        Solver solver = new Solver(puzzle);

        long start = System.currentTimeMillis();
        String[] wordsFound = solver.solveFor(wordsExpected);
        long end = System.currentTimeMillis();

        System.out.println(""+ wordsFound.length + " instances found:\n ");
        for(String found : wordsFound){
            System.out.println(found);
        }
        System.out.println("\nExecution time in milliseconds: "+ (end - start));
    }

    private static void errorOut(String msg, int status) {
        System.err.println(msg);
        exit(status);
    }
}
