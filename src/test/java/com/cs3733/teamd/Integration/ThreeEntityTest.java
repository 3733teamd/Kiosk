package com.cs3733.teamd.Integration;

import com.cs3733.teamd.Model.Node;
import com.cs3733.teamd.Model.Professional;
import com.cs3733.teamd.Model.Tag;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Me on 4/1/2017.
 * TESTS THE THREE CORE ENTITIES: TAG, NODE, PROFESSIONAL
 */
public class ThreeEntityTest {
    @Test
    public void addTagToProf(){
        Professional p = new Professional("Bob", Professional.Title.DR,"Bones");
        Tag t = new Tag("Testing");
        p.addTag(t);
        assertTrue(t.containsProf(p) && p.containsTag(t));
    }

    @Test
    public void addNodeToNode(){
        Node n1 = new Node(1,2);
        Node n2 = new Node(1,3);
        n1.addNode(n2);
        assertTrue(n2.containsNode(n1));
    }

    @Test
    public void addTagToNode(){
        Node n = new Node(1,2);
        Tag t = new Tag("Testing");
        n.addTag(t);
        assertTrue(t.containsNode(n));
    }
}
