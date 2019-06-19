package com.spenkana.wordsearch;

import com.spenkana.wordsearch.nucleus.Puzzle;

public class PuzzleSpec {
    public final String wordsExpected;
    public final Puzzle puzzle;

    public PuzzleSpec(String wordsExpected, Puzzle puzzle) {
        this.wordsExpected = wordsExpected;
        this.puzzle = puzzle;
    }
}
