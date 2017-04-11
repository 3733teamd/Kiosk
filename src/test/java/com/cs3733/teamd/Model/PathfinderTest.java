package com.cs3733.teamd.Model;

/**
 * Created by tom on 3/31/17.
 */

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;


public class PathfinderTest {
    @Test
    public void TestDistance(){
        Node a = new Node(0,0, 4);
        Node b = new Node(5,12,4 );

        Pathfinder pf = new Pathfinder(a, b);
        double dist = pf.distanceBetween(a, b);

        assertEquals(dist, 13.0, .001);
    }

    @Test
    public void TestShortestPath(){
        Node a = new Node(0, 0,4 );
        Node b = new Node(3, 0,4);
        Node c = new Node(0, -1,4);
        Node d = new Node(3, -4,4 );

        /*

        a---b
        |   |
        c   |
         \  |
          \ |
            d

         */


        a.addNode(b); a.addNode(c);
        b.addNode(d);
        c.addNode(d);

        Pathfinder pf = new Pathfinder(a,d);
        LinkedList<Node> calulatedPath = pf.shortestPath();
        LinkedList<Node> actualPath = new LinkedList<>();
        actualPath.add(d); actualPath.add(c); actualPath.add(a); // backwards path

        assertArrayEquals(calulatedPath.toArray(), actualPath.toArray());
    }

    @Test
    public void TestMultiFloor(){
        Node a = new Node(0, 0, 4);
        Node b = new Node(0, 0, 3);
        Node c = new Node(0, 2, 3);
        Node d = new Node(0, 2, 4);

        Node e = new Node(0, 0, 2);
        Node f = new Node(0, 4, 3);

        /*

        a   d           floor 4
        |   |
        b---c---f       floor 3
        |   |   |
        e   g---h       floor 2

        */


        Pathfinder pf = new Pathfinder(a,d);
    }
}
