package com.cs3733.teamd.Model;


import org.junit.Test;

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
}
