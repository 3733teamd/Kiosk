package com.cs3733.teamd.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Me on 4/1/2017.
 */
public class NodeTest {
    @Test
    public void testNodeDist(){
        Node n1 = new Node(1,2,4);
        Node n2 = new Node(1,3,4 );
        assertEquals(n1.getDist(n2),1, .005);
    }

}
