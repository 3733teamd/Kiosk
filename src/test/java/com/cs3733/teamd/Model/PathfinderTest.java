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
        Node a = new Node(0,0);
        Node b = new Node(5,12);

        Pathfinder pf = new Pathfinder(a, b);
        double dist = pf.distanceBetween(a, b);

        assertEquals(dist, 13.0, .001);
    }

    @Test
    public void TestShortestPath(){
        Node a = new Node(0, 0);
        Node b = new Node(3, 0);
        Node c = new Node(0, -1);
        Node d = new Node(3, -4);

        a.addNeighbor(b); a.addNeighbor(c);
        b.addNeighbor(d);
        c.addNeighbor(d);

        Pathfinder pf = new Pathfinder(a,d);
        LinkedList<Node> calulatedPath = pf.shortestPath();
        LinkedList<Node> actualPath = new LinkedList<>();
        actualPath.add(d); actualPath.add(c); actualPath.add(a); // backwards path
        for(Node n: calulatedPath){
            System.out.println("x: " + n.getX() + " y:" + n.getY());
        }
        assertArrayEquals(calulatedPath.toArray(), actualPath.toArray());
    }
}
