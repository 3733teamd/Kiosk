package com.cs3733.teamd.Model;

/**
 * Created by tom on 3/31/17.
 */

import org.junit.Test;
import static org.junit.Assert.*;


public class PathfinderTest {
    @Test
    public void TestDistance(){
        Node a = new Node(0,0);
        Node b = new Node(5,12);

        Pathfinder pf = new Pathfinder(a, b);
        double dist = pf.distanceBetween(a, b);

        assertEquals(dist, 13.0, .001);
    }
}
