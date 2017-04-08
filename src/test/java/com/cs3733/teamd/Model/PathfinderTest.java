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

        a.addNode(b); a.addNode(c);
        b.addNode(a); b.addNode(d);
        c.addNode(d); c.addNode(a);
        d.addNode(b); d.addNode(c);

        Pathfinder pf = new Pathfinder(a,d);
        LinkedList<Node> calulatedPath = pf.shortestPath();
        LinkedList<Node> actualPath = new LinkedList<>();
        actualPath.add(d); actualPath.add(c); actualPath.add(a); // backwards path
        for(Node n: calulatedPath){

        }
        assertArrayEquals(calulatedPath.toArray(), actualPath.toArray());
    }
}
