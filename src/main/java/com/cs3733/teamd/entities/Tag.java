package com.cs3733.teamd.entities;

import java.util.LinkedList;

/**
 * Created by Me on 3/31/2017.
 */
public class Tag {


     String trait;
     LinkedList<Node> locations = new LinkedList<Node>();

    public Tag(String trait){
        this.trait = trait;
    }

    //called to add a node and have the node add the tag
    public void addNode(Node n){
        locations.add(n);
        n.traits.add(this);
    }


}

