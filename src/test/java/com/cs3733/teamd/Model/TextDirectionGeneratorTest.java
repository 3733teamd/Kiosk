package com.cs3733.teamd.Model;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * Created by Stephen on 4/13/2017.
 */
public class TextDirectionGeneratorTest {
    @Test
    public void testGoStraight() {
        assertEquals(TextDirectionGenerator.Direction.GO_STRAIGHT,
                TextDirectionGenerator.getDirectionFromDeltas(
                        10.0, 0.0, 10.0, 0.0));
    }

    @Test
    public void testTurnLeft() {
        assertEquals(TextDirectionGenerator.Direction.TURN_RIGHT,
                TextDirectionGenerator.getDirectionFromDeltas(
                        10.0, 0.0, 0.0, 10.0));
    }

    @Test
    public void testBasicEnglishDirections(){
        List<String> expectedOutput = new ArrayList<String>(
                Arrays.asList(
                        "Proceed straight",
                        "and then turn left",
                        "and then you have arrived at your destination")
        );
        assertEquals(expectedOutput, TextDirectionGenerator.getEnglishDirections(
                new ArrayList<TextDirectionGenerator.Direction>(
                        Arrays.asList(
                                TextDirectionGenerator.Direction.GO_STRAIGHT,
                                TextDirectionGenerator.Direction.TURN_LEFT,
                                TextDirectionGenerator.Direction.ARRIVED)
                )
        ));
    }
}
