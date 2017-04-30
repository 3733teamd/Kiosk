package com.cs3733.teamd.Model;

import com.cs3733.teamd.Model.Entities.Node;
import org.junit.Test;

import static com.cs3733.teamd.Model.DirectionType.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by kicknickr on 4/27/2017.
 */
public class DirectionTest {
    @Test
    public void testGoStraight1() {
        assertEquals(GO_STRAIGHT, Direction.deriveDirectionType(
                new Node(0,0,0), new Node(0,10,0), new Node(0,15,0)));
    }

    @Test
    public void testGoStraight2() {
        assertEquals(GO_STRAIGHT, Direction.deriveDirectionType(
                new Node(0,0,0), new Node(0,10,0), new Node(-1,15,0)));
    }

    @Test
    public void testSlightRight() {
        assertEquals(SLIGHT_RIGHT, Direction.deriveDirectionType(
                new Node(0,0,0), new Node(0,10,0), new Node(3,15,0)));
    }

    @Test
    public void testRight() {
        assertEquals(TURN_RIGHT, Direction.deriveDirectionType(
                new Node(0,0,0), new Node(0,10,0), new Node(6,15,0)));
    }

//    @Test
//    public void testSharpLeft() {
//        assertEquals(SHARP_LEFT, Direction.deriveDirectionType(
//                new Node(0,0,0), new Node(0,10,0), new Node(-1,8,0)));
//    }
//
//    @Test
//    public void testSharpRight() {
//        assertEquals(SHARP_RIGHT, Direction.deriveDirectionType(
//                new Node(0,0,0), new Node(0,10,0), new Node(1,8,0)));
//    }
}
