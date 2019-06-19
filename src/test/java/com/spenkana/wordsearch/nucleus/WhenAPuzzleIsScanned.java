package com.spenkana.wordsearch.nucleus;

import com.spenkana.wordsearch.ExpectedObjects;
import com.spenkana.wordsearch.nucleus.Solver.Found;
import org.junit.jupiter.api.Test;


import java.io.Console;
import java.util.List;

import static com.spenkana.wordsearch.nucleus.Puzzle.newPuzzle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WhenAPuzzleIsScanned {

    Puzzle FiveBy5 = newPuzzle(
            "ABCDE",
            "FGHIJ",
            "KLMNO",
            "PQRST",
            "UVWXY"

    ).output;

    @Test
    public void singleCharWordIsFound() {
        String word = "E";
        Puzzle puzzle = newPuzzle(word).output;
        verifyFind(word, puzzle);
    }

    @Test
    public void horizontalScanSucceedsAtOrigin() {
        String word = "AB";
        Puzzle puzzle = newPuzzle(word, "CD").output;
        verifyFind(word, puzzle);
    }

    @Test
    public void horizontalScanSucceedsAnywhereOnFirstLine() {
        String word = "BC";
        Puzzle puzzle = newPuzzle("ABC", "DEF", "GHI").output;
        verifyFind(word, puzzle);
    }

    @Test
    public void horizontalScanSucceedsAnywhere() {
        String word = "HI";
        Puzzle puzzle = newPuzzle("ABC", "DEF", "GHI").output;
        verifyFind(word, puzzle);
    }

    @Test
    public void downwardVerticalScanSucceeds() {
        verifyFind("GLQ", FiveBy5);
    }

    @Test
    public void backwardHorizontalScanSucceeds() {
        verifyFind("IHG", FiveBy5);
    }

    @Test
    public void upwardVerticalScanSucceeds() {
        verifyFind("QLG", FiveBy5);
    }

    @Test
    public void downRightScanSucceeds() {
        verifyFind("GMS", FiveBy5);
    }

    @Test
    public void upLeftScanSucceeds() {
        verifyFind("SMG", FiveBy5);
    }

    @Test
    public void downLeftScanSucceeds() {
        verifyFind("IMQ", FiveBy5);
    }

    @Test
    public void upRightScanSucceeds() {
        verifyFind("QMI", FiveBy5);
    }

    @Test
    public void expectedWordsFoundIn15x15() {
        Solver solver = new Solver(ExpectedObjects.ExamplePuzzle);

        List<Found> instancesFound = solver.find(ExpectedObjects.wordsExpectedInExampleAsArray);

        assertEquals(23, instancesFound.size());
        for (String word : ExpectedObjects.wordsExpectedInExampleAsArray) {
            verifyFind(word, ExpectedObjects.ExamplePuzzle);
        }
    }

    @Test
    public void expectedSolutionIsFormattedCorrectly() {
        Solver solver = new Solver(ExpectedObjects.ExamplePuzzle);

        String[] solution = solver.solveFor(ExpectedObjects.wordsExpectedInExampleAsArray);

        assertEquals(ExpectedObjects.expectedSolution.length, solution.length);
        for(int i = 0; i < solution.length; ++i){
            String line = solution[i];
            display(line);
            assertEquals(ExpectedObjects.expectedSolution[i], line);
        }
    }

    private void display(String line) {
        Console console = System.console();
        if(console != null){
            console.printf(line);
        } else {
            System.out.println(line);
        }
    }


    @Test
    public void scanCanBeRestrictedToStraightInstances() {
        String[] words = new String[]{
                "BONES", "KHAN", "KIRK", "SCOTTY", "SPOCK", "SULU", "UHURA"
        };
        Solver solver = new Solver(ExpectedObjects.ExamplePuzzle);

        List<Found> instancesFound = solver.findStraightInstancesOnly(words);

        for (Found found : instancesFound) {
            Puzzle.Cell[] cells = found.cells;
            int xOffset = cells[1].x - cells[0].x;
            int yOffset = cells[1].y - cells[0].y;
            for (int i = 2; i < cells.length; ++i) {
                if ((cells[i].x - cells[i - 1].x) != xOffset) {
                    fail("Bad x offset - instance #" + 1);
                }
                if ((cells[i].y - cells[i - 1].y) != yOffset) {
                    fail("Bad y offset - instance #" + 1);
                }
            }
        }
    }


    @Test
    public void scanFindsMultipleInstancesOfAWord() {
        /*
        Note that in this puzzle, the four "corner" Bs have five neighboring Cs,
        resulting in five instances of ABC each for a total of 20, The five
        "side" Bs only have three neighboring Cs, resulting in three instances
         each for a total of twelve. Thus there are 32 instances of ABC.
         */
        Puzzle puzzle = newPuzzle(
                "CCCCC",
                "CBBBC",
                "CBABC",
                "CBBBC",
                "CCCCC"
        ).output;

        List<Found> instancesFound = new Solver(puzzle).find("ABC");

        assertEquals(32, instancesFound.size());
        for (Found found : instancesFound) {
            verifyWordFound("ABC", found);
        }
    }


    @Test
    public void twoWordsAreFound() {
        String word1 = "BCD";
        String word2 = "GHI";
        Solver solver = new Solver(FiveBy5);

        List<Found> wordsFound = solver.find(word1, word2);

        assertEquals(2, wordsFound.size());
        verifyWordFound(word1, wordsFound.get(0));
        verifyWordFound(word2, wordsFound.get(1));
    }


    private void verifyFind(String word, Puzzle fiveBy5) {
        Solver solver = new Solver(fiveBy5);

        List<Found> wordsFound = solver.find(word);

        verifyWordFound(word, wordsFound.get(0));
    }

    private void verifyWordFound(String word, Found found) {
        assertEquals(found.word, word);
        int expectedLength = word.length();
        assertEquals(expectedLength, found.cells.length);
        for (int i = 0; i < expectedLength; ++i) {
            assertEquals(word.charAt(i), found.cells[i].value);
        }
    }


}
