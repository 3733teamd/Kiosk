package com.cs3733.teamd.Integration;

import com.cs3733.teamd.Model.Entities.Node;
import com.cs3733.teamd.Model.Entities.ProTitle;
import com.cs3733.teamd.Model.Entities.Professional;
import com.cs3733.teamd.Model.Entities.Tag;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Me on 4/1/2017.
 * TESTS THE THREE CORE ENTITIES: TAG, NODE, PROFESSIONAL
 */
public class ThreeEntityTest {
    @Test
    public void addTagToProf(){
        Professional p = new Professional("Bob", new ArrayList<ProTitle>(), 10);
        Tag t = new Tag("Testing",2);
        p.addTag(t);
        assertTrue(t.containsProf(p));
        assertTrue(p.containsTag(t));
    }

    @Test
    public void addNodeToNode(){
        Node n1 = new Node(1,2,4);
        Node n2 = new Node(1,3,4);
        n1.addNode(n2);
        assertTrue(n2.containsNode(n1));
    }

    @Test
    public void addTagToNode(){
        Node n = new Node(1,2,4);
        Tag t = new Tag("Testing",4);
        n.addTag(t);
        assertTrue(t.containsNode(n));
    }
}
