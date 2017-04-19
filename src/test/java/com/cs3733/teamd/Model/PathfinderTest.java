package com.cs3733.teamd.Model;

/**
 * Created by tom on 3/31/17.
 */

import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Exceptions.PathNotFoundException;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;


public class PathfinderTest {
    @Test
    public void testDistance(){
        Node a = new Node(0,0, 4);
        Node b = new Node(5,12,4 );

        Pathfinder pf = new Pathfinder(a, b);
        double dist = pf.distanceBetween(a, b);

        assertEquals(dist, 13.0, .001);
    }

    @Test
    public void testShortestPath(){
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
        LinkedList<Node> calculatedPath = new LinkedList<>();
        try {
            calculatedPath = pf.shortestPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LinkedList<Node> actualPath = new LinkedList<>();
        actualPath.add(d); actualPath.add(c); actualPath.add(a); // backwards path

        assertArrayEquals(calculatedPath.toArray(), actualPath.toArray());
    }

    @Test
    public void testMultiFloor(){
        Node a = new Node(0, 0, 4);
        Node b = new Node(0, 0, 3);
        Node c = new Node(0, 2, 3);
        Node d = new Node(0, 2, 4);
        Node e = new Node(0, 0, 2);
        Node f = new Node(0, 4, 3);
        Node g = new Node(0, 2, 2);
        Node h = new Node(0, 4, 2);

        a.addNode(b);
        b.addNode(e); b.addNode(c);
        c.addNode(g); c.addNode(d);
        g.addNode(h);
        h.addNode(f);

        /*

        a   d           floor 4
        |   |
        b---c   f       floor 3
        |   |   |
        e   g---h       floor 2

        */


        Pathfinder pf = new Pathfinder(a,f);
        LinkedList<Node> calculatedPath = new LinkedList<>();
        try {
            calculatedPath = pf.shortestPath();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        LinkedList<Node> actualPath = new LinkedList<>();
        actualPath.add(f); actualPath.add(h); actualPath.add(g); actualPath.add(c); actualPath.add(b); actualPath.add(a); // backwards path

        assertArrayEquals(calculatedPath.toArray(), actualPath.toArray());

    }

    @Test
    public void testSameFloorPriority() {

        Node i = new Node(0, 0, 4);
        Node j = new Node(1, 0, 4);
        Node k = new Node(0, -1, 4);

        Node l = new Node(1, 0, 3);
        Node m = new Node(0, -1, 3);

        i.addNode(k); i.addNode(j);
        m.addNode(l); m.addNode(k);
        l.addNode(j);

        /*
        should choose the route that stays on the same floor over the shortest route

        i---j               l
        |                 /
        k               m

        */

        Pathfinder pf = new Pathfinder(k, j);

        LinkedList<Node> calculatedPath = new LinkedList<>();
        try {
            calculatedPath = pf.shortestPath();
        } catch (PathNotFoundException pnfe){
            pnfe.printStackTrace();
        }

        LinkedList<Node> actualPath = new LinkedList<>();
        actualPath.add(j); actualPath.add(i); actualPath.add(k); // backwards path

        // show the k-m-l-j path is shorter
        assertTrue((k.getDist(m) + m.getDist(l) + l.getDist(j)) < (k.getDist(i) + i.getDist(j)));

        // pathfinder correctly chooses longer k-i-j path, because it stays on the same floor
        assertArrayEquals(calculatedPath.toArray(), actualPath.toArray());

    }


    @Test
    public void testMultipleEndPoint(){
        Node a = new Node(0, 0, 4);
        Node b = new Node(0, 0, 3);
        Node c = new Node(0, 400, 4);

        a.addNode(b); a.addNode(c);
        Pathfinder pf = new Pathfinder(a, new LinkedList<>(Arrays.asList(b,c)));
        LinkedList<Node> calculatedPath = new LinkedList<>();
        LinkedList<Node> actualPath = new LinkedList<>();
        try {
            calculatedPath = pf.shortestPath();
        } catch (PathNotFoundException e) {
            e.printStackTrace();
        }
        actualPath.add(c); actualPath.add(a); // backwards path

        assertArrayEquals(calculatedPath.toArray(), actualPath.toArray());

        Node d = new Node(0, 0, 4);
        Node e = new Node(0, 0, 3);
        Node f = new Node(0, 700, 4);

        d.addNode(e); d.addNode(f);
        pf = new Pathfinder(d, new LinkedList<>(Arrays.asList(e, f)));
        calculatedPath = new LinkedList<>();
        actualPath = new LinkedList<>();
        try {
            calculatedPath = pf.shortestPath();
        } catch (PathNotFoundException ex) {
            ex.printStackTrace();
        }
        actualPath.add(e); actualPath.add(d); // backwards path

        assertArrayEquals(calculatedPath.toArray(), actualPath.toArray());
    }

    @Test
    public void testAllAlgorithms(){
        ApplicationConfiguration ac = ApplicationConfiguration.getInstance();

        ac.setCurrentSearchAlgorithm(ApplicationConfiguration.SearchAlgorithm.DFS);

        testShortestPath();
        testMultiFloor();
        testSameFloorPriority();
        testMultipleEndPoint();

        ac.setCurrentSearchAlgorithm(ApplicationConfiguration.SearchAlgorithm.BFS);

        testShortestPath();
        testMultiFloor();
        testSameFloorPriority();
        testMultipleEndPoint();

    }
}
